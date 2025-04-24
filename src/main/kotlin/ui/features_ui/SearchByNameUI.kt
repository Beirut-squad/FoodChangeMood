package org.example.ui.features_ui

import org.example.error.ThereIsNoNameException
import org.example.logic.use_case.SearchByNameUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.utils.Colors
import org.example.utils.Strings
import java.util.*

class SearchByNameUI(
    private val searchByNameUseCase: SearchByNameUseCase,
    private val viewer: Viewer,
    private val reader: Reader,
) {
    fun show() {
        displaySearchHeader()
        try {
            val recipe: Recipe? = searchRecipe()
            if (recipe != null) displayRecipe(recipe)
            else displayNoRecipeFoundMessage()
        } catch (e: ThereIsNoNameException) {
            handleSearchError(e)
        }
    }

    private fun displaySearchHeader() {
        viewer.printTitle(Strings.SEARCH_BY_NAME_TITLE.message)
    }

    private fun searchRecipe(): Recipe? {
        val userInput = reader.readInput().toString()
        return searchByNameUseCase.searchRecipeByName(userInput)
    }

    private fun displayRecipe(recipe: Recipe) {
        displayFoundRecipe(recipe)
        displayRecipeDetails(recipe)
    }

    private fun displayFoundRecipe(recipe: Recipe) {
        viewer.printLoader("\n-------------------------------\n")
        viewer.printCorrectOutput(Strings.FOUNT_RECIPE.formatMessage(recipe.name))
        viewer.printLoader("-------------------------------\n")
    }

    private fun displayRecipeDetails(recipe: Recipe) {
        viewer.printCorrectOutput(RecipeFormatter.format(recipe))
    }

    private fun displayNoRecipeFoundMessage() {
        viewer.printError(Strings.SORRY_COULD_NOT_FIND_RECIPE_MATCHES_NAME.message)
    }

    private fun handleSearchError(error: ThereIsNoNameException) {
        viewer.printError(Strings.ERROR_OCCURRED_WHILE_SEARCHING.formatMessage(error.message))
    }
}