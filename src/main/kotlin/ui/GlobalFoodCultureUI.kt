package org.example.ui

import org.example.logic.GlobalFoodCultureUseCase
import org.example.logic.Validator
import org.example.model.Recipe

class GlobalFoodCultureUI(
    private val globalFoodCultureUseCase: GlobalFoodCultureUseCase,
    private val validator: Validator
) {
    fun displayCountryFoodCulture() {
        var shouldContinue = true
        while (shouldContinue) {
            print("Enter a country to explore its meals (or 0 to exit): ")
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
        println("Please enter a country name using letters only.")
    }



    private fun showInputError() {
        println("Please enter a valid country name. ")
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
        println("No meals found for '$country'")
        println("Try another country.")
    }

    private fun showRecipes(country: String, recipes: List<Recipe>) {
        println("${recipes.size} ${if (recipes.size == 1) "meal" else "meals"} found for '$country':\n")
        recipes.forEach {
            // TODO: The official format of the recipe must be displayed.
            println(it)
        }
    }

}