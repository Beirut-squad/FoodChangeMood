package ui.features_ui

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.example.logic.Validator
import org.example.logic.use_case.GlobalFoodCultureUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.ui.features_ui.GlobalFoodCultureUI
import org.example.utils.Colors
import org.example.utils.Strings
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GlobalFoodCultureUITest {
    private lateinit var validator: Validator
    private lateinit var reader: Reader
    private lateinit var viewer: Viewer
    private lateinit var globalFoodCultureUseCase: GlobalFoodCultureUseCase
    private lateinit var ui: GlobalFoodCultureUI

    @BeforeEach
    fun setUp() {
        val colors = Colors()
        this.viewer = spyk(Viewer(colors))
        this.reader = mockk(relaxed = true)
        this.validator = mockk(relaxed = true)
        this.globalFoodCultureUseCase = mockk(relaxed = true)
        this.ui = GlobalFoodCultureUI(globalFoodCultureUseCase, validator, viewer, reader)

    }

    @Test
    fun `should display title and exit when enters exit command`() {
        // Given
        mockUserInputs("0")

        // When
        ui.show()

        // Then
        verify(exactly = 1) {
            viewer.printTitle(Strings.ENTER_COUNTRY_OR_EXIT.message)
        }
    }

    @Test
    fun `should display invalid error when country input is empty`() {
        // Given
        mockUserInputs(" ", "0")

        // When
        ui.show()

        // Then
        verify(exactly = 1) {
            viewer.printError(Strings.INVALID_COUNTRY_NAME.message)
        }
    }

    @Test
    fun `should display invalid input error when country input is null`() {
        // Given
        mockUserInputs(null, "0")

        // When
        ui.show()

        // Then
        verify(exactly = 1) {
            viewer.printError(Strings.INVALID_COUNTRY_NAME.message)
        }
    }

    @Test
    fun `should call getRandomMealsByCountry function once from globalFoodCultureUseCase when valid country is entered`() {
        // Given
        val country = "valid Country"
        mockUserInputs(country, "0")
        mockSuccessfulValidation(country)

        // When
        ui.show()

        // Then
        verify(exactly = 1) {
            globalFoodCultureUseCase.getRandomMealsByCountry(country)
        }
    }

    @Test
    fun `should display invalid error  when country contains non-alphabetic characters`() {
        // Given
        val invalidCountry = "invalid 123"
        mockUserInputs(invalidCountry, "0")
        mockFailedValidation(invalidCountry)

        // When
        ui.show()

        // Then
        verify(exactly = 1) {
            viewer.printError(Strings.INVALID_COUNTRY_NAME_ENTER_LETTERS.message)
        }
    }

    @Test
    fun `should display not found message when no meals exist for country`() {
        // Given
        val country = "Gaza"
        mockUserInputs(country, "0")
        mockSuccessfulValidation(country)
        mockEmptyMealsList(country)

        // When
        ui.show()

        // Then
        verify(exactly = 1) {
            viewer.printError(Strings.NO_MEALS_FOUND_FOR_COUNTRY.formatMessage(country))
            viewer.printInfoLine(Strings.TRY_ANOTHER_COUNTRY.message)
        }
    }

    @Test
    fun `should display single recipe when one meal exists for country`() {
        // Given
        val country = "Gaza"
        mockUserInputs(country, "0")
        mockSuccessfulValidation(country)
        val recipes = listOf(Recipe(name = "Muskhan recipes", description = "muskhan is the famous meals in GaZa"))
        mockMealsList(country, recipes)

        // When
        ui.show()

        // Then
        verify(exactly = 1) {
            viewer.printCorrectOutput("${recipes.size} ${if (recipes.size == 1) "meal" else "meals"} found for '$country':\n")
            recipes.forEach {
                viewer.printPlainText(RecipeFormatter.format(it))
            }
        }
    }

    @Test
    fun `show should display multiple recipes when multiple meals exist for country`() {
        // Given
        val country = "Gaza"
        mockUserInputs(country, "0")
        mockSuccessfulValidation(country)
        val recipes = listOf(
            Recipe(name = "Gaza recipes"),
            Recipe(name = "Muskhan recipes", description = "muskhan is the famous meals in GaZa"),
            Recipe(name = "Doqaa recipes", tags = listOf("palestine", "gaza")),
        )
        mockMealsList(country, recipes)

        // When
        ui.show()

        // Then
        verify(exactly = 1) {
            viewer.printCorrectOutput("${recipes.size} ${if (recipes.size == 1) "meal" else "meals"} found for '$country':\n")
            recipes.forEach {
                viewer.printPlainText(RecipeFormatter.format(it))
            }
        }
    }

    // Helper functions
    private fun mockUserInputs(vararg inputs: Any?) {
        val iterator = inputs.toList().iterator()
        every { reader.readInput() } answers { iterator.next()?.toString()?.trim() }
    }

    private fun mockSuccessfulValidation(country: String) {
        every { validator.validateIsAlphabetic(country) } returns true
    }

    private fun mockFailedValidation(country: String) {
        every { validator.validateIsAlphabetic(country) } returns false
    }

    private fun mockEmptyMealsList(country: String) {
        every { globalFoodCultureUseCase.getRandomMealsByCountry(country) } returns emptyList()
    }

    private fun mockMealsList(country: String, recipes: List<Recipe>) {
        every { globalFoodCultureUseCase.getRandomMealsByCountry(country) } returns recipes
    }
}