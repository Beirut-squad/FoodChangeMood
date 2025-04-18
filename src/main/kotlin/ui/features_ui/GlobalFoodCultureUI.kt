package org.example.ui.features_ui

import org.example.logic.use_case.GlobalFoodCultureUseCase
import org.example.logic.Validator
import org.example.model.Recipe
import org.example.ui.RecipeFormatter

import org.example.utils.Colors

class GlobalFoodCultureUI(
    private val globalFoodCultureUseCase: GlobalFoodCultureUseCase,
    private val validator: Validator,
    private val colors: Colors
) {
    fun show() {
        var shouldContinue = true
        while (shouldContinue) {
            print(colors.cyan("Enter a country to explore its meals (or 0 to exit): "))
            val input = readlnOrNull()?.trim()
            shouldContinue = processInput(input)
        }
    }

    private fun processInput(input: String?): Boolean {
        return when {
            input == "0" -> false
            input.isNullOrEmpty() -> {
                showInputError()
                true
            }
            !validator.vaildateIsAlphabetic(input) -> {
                showCountryNameWithoutLetters()
                true
            }
            else -> handleCountryInput(input)
        }
    }

    private fun showCountryNameWithoutLetters(){
        println(colors.red("Please enter a country name using letters only."))
    }



    private fun showInputError() {
        println(colors.red("Please enter a valid country name. "))
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
        println(colors.red("No meals found for '$country'"))
        println(colors.yellow("Try another country."))
    }

    private fun showRecipes(country: String, recipes: List<Recipe>) {
        println(colors.green("${recipes.size} ${if (recipes.size == 1) "meal" else "meals"} found for '$country':\n"))
        recipes.forEach {
            println(RecipeFormatter.format(it))
        }
    }

}