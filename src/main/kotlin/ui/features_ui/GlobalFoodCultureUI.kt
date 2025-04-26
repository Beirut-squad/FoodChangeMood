package org.example.ui.features_ui

import org.example.logic.use_case.GlobalFoodCultureUseCase
import org.example.logic.Validator
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.utils.Strings
import ui.Display

class GlobalFoodCultureUI(
    private val globalFoodCultureUseCase: GlobalFoodCultureUseCase,
    private val validator: Validator,
    private val viewer: Viewer,
    private val reader: Reader
) : Display {
    companion object {
        const val exit = "0"
    }

    override fun show() {
        var shouldContinue = true
        while (shouldContinue) {
            viewer.printTitle(Strings.ENTER_COUNTRY_OR_EXIT.message)
            var input = reader.readInput()?.trim()
            shouldContinue = processInput(input)
        }
    }

    private fun processInput(input: String?): Boolean {
        return when {
            input == exit -> false
            input.isNullOrEmpty() -> {
                showInputError()
                true
            }
            !validator.validateIsAlphabetic(input) -> {
                showCountryNameWithoutLetters()
                true
            }
            else -> handleCountryInput(input)
        }
    }

    private fun showCountryNameWithoutLetters() {
        viewer.printError(Strings.INVALID_COUNTRY_NAME_ENTER_LETTERS.message)
    }


    private fun showInputError() {
        viewer.printError(Strings.INVALID_COUNTRY_NAME.message)
    }

    private fun handleCountryInput(country: String): Boolean {
        val recipes = globalFoodCultureUseCase.getRandomMealsByCountry(country)
        if (recipes.isEmpty()) {
            showNoRecipesFound(country)
            return true
        } else {
            showRecipes(country, recipes)
            return false
        }
    }

    private fun showNoRecipesFound(country: String) {
        viewer.printError(Strings.NO_MEALS_FOUND_FOR_COUNTRY.formatMessage(country))
        viewer.printInfoLine(Strings.TRY_ANOTHER_COUNTRY.message)
    }

    private fun showRecipes(country: String, recipes: List<Recipe>) {
        viewer.printCorrectOutput("${recipes.size} ${if (recipes.size == 1) "meal" else "meals"} found for '$country':\n")
        recipes.forEach {
            viewer.printPlainText(RecipeFormatter.format(it))
        }
    }

}