package ui.features_ui

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.error.ThereIsNoNameException
import org.example.logic.use_case.SearchByNameUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.ui.features_ui.SearchByNameUI
import org.example.utils.Strings
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchByNameUITest {

    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var ui: SearchByNameUI
    private lateinit var useCase: SearchByNameUseCase
    private val input = "Gaza"
    private val gazaRecipe = Recipe(name = "Gaza Recipe")
    private val gazaRecipe2 = Recipe(name = "Original Gaza Recipe", description = "desc...")

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        viewer = mockk(relaxed = true)
        reader = mockk(relaxed = true)
        ui = SearchByNameUI(useCase, viewer, reader)
    }

    @Test
    fun `show() should call searchRecipeByName once`() {
        //When
        ui.show()

        //Then
        verify(exactly = 1) {
            useCase.searchRecipeByName("")
        }
    }

    @Test
    fun `should display title when user call show()`() {
        //When
        ui.show()

        //Then
        verify(exactly = 1) {
            viewer.printTitle(Strings.SEARCH_BY_NAME_TITLE.message)
        }
    }

    @Test
    fun `should display number of recipes found when user enter recipe name and found`() {
        //Given
        val recipes = listOf(gazaRecipe, gazaRecipe2)
        every { reader.readInput() } returns input
        every { useCase.searchRecipeByName(input) } returns recipes

        //When
        ui.show()

        //Then
        verify(exactly = 1) {
            viewer.printCorrectOutput(Strings.FOUNT_RECIPE.formatMessage(recipes.size.toString()))
        }
    }

    @Test
    fun `should display info recipe when user enter recipe name and found`() {
        //Given
        every { reader.readInput() } returns input
        every { useCase.searchRecipeByName(input) } returns listOf(gazaRecipe)

        //When
        ui.show()

        //Then
        verify(exactly = 1) {
            viewer.printCorrectOutput(RecipeFormatter.format(gazaRecipe))
        }
    }

    @Test
    fun `should show error message when recipe is not found`() {
        //Given
        every { reader.readInput() } returns input
        every { useCase.searchRecipeByName(input) } returns null

        //When
        ui.show()

        //Then
        verify(exactly = 1) {
            viewer.printError(Strings.SORRY_COULD_NOT_FIND_RECIPE_MATCHES_NAME.message)
        }
    }

    @Test
    fun `should print error when search throws ThereIsNoNameException`() {
        // Given
        every { reader.readInput() } returns input
        val errorMessage = "Recipe not found"
        every { useCase.searchRecipeByName(input) } throws ThereIsNoNameException(errorMessage)

        // When
        ui.show()

        // Then
        verify(exactly = 1) {
            viewer.printError(Strings.ERROR_OCCURRED_WHILE_SEARCHING.formatMessage(errorMessage))
        }
    }

}