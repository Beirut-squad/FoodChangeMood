package ui.features_ui

import io.mockk.*
import org.example.logic.Validator
import org.example.logic.use_case.HealthyRecipesUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.ui.features_ui.HealthyFoodRecipesUi
import org.example.utils.Colors
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.logging.Formatter

class HealthyFoodRecipesUiTest {

    private lateinit var validator: Validator
    private lateinit var healthyRecipesUseCase: HealthyRecipesUseCase
    private lateinit var healthyFoodRecipesUi: HealthyFoodRecipesUi
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader

    @BeforeEach
    fun setup() {
        validator = mockk(relaxed = true)
        healthyRecipesUseCase = mockk(relaxed = true)
        viewer = mockk(relaxed = true)
        reader = mockk(relaxed = true)

        healthyFoodRecipesUi = HealthyFoodRecipesUi(validator, healthyRecipesUseCase, viewer, reader)

    }

    @Test
    fun `should show a cyan-colored question for the number of meals the user want to see`() {

        // When
        healthyFoodRecipesUi.show()

        // Then
        verify { viewer.printTitle("Enter the number of meals you want") }
    }

    @Test
    fun `should show a red invalid input message when the user input is null`() {
        // Given
        every { reader.readInt() } returns null

        // When
        healthyFoodRecipesUi.show()

        // Then
        verify { viewer.printError("Invalid Input, Enter a Positive Number") }
    }

    @Test
    fun `should show a red invalid input message when the user input is invalid number`() {
        // Given
        every { reader.readInt() } returns 5
        every { validator.validateRecipesCountInput(any()) } returns false

        // When
        healthyFoodRecipesUi.show()

        // Then
        verify { viewer.printError("Invalid Input, Enter a Positive Number") }
    }

    @Test
    fun `should show loading message when the user enters a valid input`() {
        // Given
        every { reader.readInt() } returns 5
        every { validator.validateRecipesCountInput(any()) } returns true

        // When
        healthyFoodRecipesUi.show()

        // Then

        verify { viewer.printLoader("Loading...") }
    }

    @Test
    fun `should show a no recipes available message when there is no healthy recipes found`() {
        // Given
        every { reader.readInt() } returns 5
        every { validator.validateRecipesCountInput(any()) } returns true
        every { healthyRecipesUseCase.getHealthyRecipes(any()) } returns emptyList()

        // When
        healthyFoodRecipesUi.show()

        // Then
        verify { viewer.printError("No Recipes Available") }
    }

    @Test
    fun `should show the recipes info when there is available recipes`() {
        // Given
        every { reader.readInt() } returns 5
        every { validator.validateRecipesCountInput(any()) } returns true
        val fakeRecipesList = List(5) { mockk<Recipe>(relaxed = true) }
        every { healthyRecipesUseCase.getHealthyRecipes(any()) } returns fakeRecipesList

        mockkObject(RecipeFormatter)
        every { RecipeFormatter.format(any()) } returns "Fake Recipe Info"

        // When
        healthyFoodRecipesUi.show()

        // Then
        verify(exactly = 5) { viewer.printPlainText("Fake Recipe Info") }
    }
}