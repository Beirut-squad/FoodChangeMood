package ui.features_ui

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.example.logic.Validator
import org.example.logic.use_case.HealthyRecipesUseCase
import org.example.ui.features_ui.HealthyFoodRecipesUi
import org.example.utils.Colors
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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



}