package org.example.ui.features_ui

import org.example.error.ThereIsNoNameException
import org.example.logic.use_case.SearchByNameUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.utils.Strings
import kotlin.collections.List

class SearchByNameUI(
    private val searchByNameUseCase: SearchByNameUseCase,
    private val viewer: Viewer,
    private val reader: Reader,
) {
    fun show() {
        displaySearchHeader()
        try {
            val recipes: List<Recipe>? = searchRecipe()
            if (recipes?.isNotEmpty() == true) displayRecipe(recipes)
            else displayNoRecipeFoundMessage()
        } catch (e: ThereIsNoNameException) {
            handleSearchError(e)
        }
    }

    private fun displaySearchHeader() {
        viewer.printTitle(Strings.SEARCH_BY_NAME_TITLE.message)
    }

    private fun searchRecipe(): List<Recipe>? {
        val userInput = reader.readInput().toString()
        return searchByNameUseCase.searchRecipeByName(userInput)
    }

    private fun displayRecipe(recipes: List<Recipe>) {
        displayFoundRecipe(recipes)
        recipes.forEach {
            displayRecipeDetails(it)
        }
    }

    private fun displayFoundRecipe(recipes: List<Recipe>) {
        viewer.printLoader("\n-------------------------------\n")
        viewer.printCorrectOutput(Strings.FOUNT_RECIPE.formatMessage(recipes.size.toString()))
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