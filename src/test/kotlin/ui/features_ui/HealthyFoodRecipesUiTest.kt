package ui.features_ui

import io.mockk.*
import org.example.logic.Validator
import org.example.logic.use_case.HealthyRecipesUseCase
import org.example.model.Recipe
import org.example.ui.RecipeFormatter
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
    private lateinit var colors: Colors
    private lateinit var healthyFoodRecipesUi: HealthyFoodRecipesUi
    @BeforeEach
    fun setup() {
        validator = mockk(relaxed = true)
        healthyRecipesUseCase = mockk(relaxed = true)
        colors = mockk(relaxed = true)
        healthyFoodRecipesUi = spyk(HealthyFoodRecipesUi(validator, healthyRecipesUseCase, colors))
    }

    @Test
    fun `should show a cyan-colored question for the number of meals the user want to see`(){
        // When
        healthyFoodRecipesUi.show()

        // Then
        verify { healthyFoodRecipesUi.outputPrinter(colors.cyan("Enter the number of meals you want"))}
    }


    @Test
    fun `should show a red invalid input message when the user input is null`(){
        // Given
        every { healthyFoodRecipesUi.getUserInput() } returns null

        // When
        healthyFoodRecipesUi.show()

        // Then
        verify { healthyFoodRecipesUi.outputPrinter(colors.red("Invalid Input, Enter a Positive Number")) }
    }

    @Test
    fun `should show a red invalid input message when the user input is invalid number`(){
        // Given
        every { validator.validateRecipesCountInput(any()) } returns false

        // When
        healthyFoodRecipesUi.show()

        // Then
        verify { healthyFoodRecipesUi.outputPrinter(colors.red("Invalid Input, Enter a Positive Number")) }
    }

    @Test
    fun `should call displayRecipeInfo when the user enters a valid input`(){
        // Given
        every { healthyFoodRecipesUi.getUserInput() } returns 5
        every { validator.validateRecipesCountInput(any()) } returns true

        // When
        healthyFoodRecipesUi.show()

        // Then
        verify { healthyFoodRecipesUi.displayRecipeInfo(any()) }
    }

    @Test
    fun `should show loading message when displayRecipeInfo is called`(){
        // When
        healthyFoodRecipesUi.displayRecipeInfo(5)

        // Then
        verify { healthyFoodRecipesUi.outputPrinter(colors.blue("Loading...")) }
    }


    @Test
    fun `should show a no recipes available message when there is no healthy recipes found`(){
        // Given
        every { healthyRecipesUseCase.getHealthyRecipes(any()) } returns emptyList()

        // When
        healthyFoodRecipesUi.displayRecipeInfo(5)

        // Then
        verify { healthyFoodRecipesUi.outputPrinter(colors.red("No Recipes Available")) }
    }

//    @Disabled
//    @Test
//    fun `should show the recipes info when there is available recipes`(){
//        // Given
//        val fakeRecipesList = List(5) { mockk<Recipe>() }
//        every { healthyRecipesUseCase.getHealthyRecipes(any())} returns fakeRecipesList
//        every { recipeFormatter.format(any()) } returns "Formated recipe"
//
//        // When
//        healthyFoodRecipesUi.displayRecipeInfo(5)
//
//        // Then
//        verify(exactly = 5) { healthyFoodRecipesUi.outputPrinter(any()) }
//    }

    @Test
    fun `should show the recipes info when there is available recipes`() {
        // Given
        val fakeRecipesList = List(5) { mockk<Recipe>(relaxed = true) }

        every { healthyRecipesUseCase.getHealthyRecipes(any()) } returns fakeRecipesList
        mockkObject(RecipeFormatter)
        every { RecipeFormatter.format(any()) } returns "Fake Recipe Info"

        // When
        healthyFoodRecipesUi.displayRecipeInfo(5)

        // Then
        verify(exactly = 5) { healthyFoodRecipesUi.outputPrinter("Fake Recipe Info") }
    }



}