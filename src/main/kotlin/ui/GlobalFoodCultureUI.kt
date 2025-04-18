package org.example.ui

import org.example.logic.GlobalFoodCultureUseCase
import org.example.model.Recipe

class GlobalFoodCultureUI(private val globalFoodCultureUseCase: GlobalFoodCultureUseCase) {

    fun displayCountryFoodCulture() {
        print("Enter a country to explore its meals (or 0 to exit): ")
        var shouldContinue = true
        while (shouldContinue) {
            when (val input = readlnOrNull()?.trim()) {
                "0" -> shouldContinue = false
                null, "" -> showInputError()
                else -> shouldContinue = handleCountryInput(input)
            }
        }
    }

    private fun showInputError() {
        print("Please enter a valid country name: ")
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
        print("Try another country (or 0 to exit): ")
    }

    private fun showRecipes(country: String, recipes: List<Recipe>) {
        println("${recipes.size} ${if (recipes.size == 1) "meal" else "meals"} found for '$country':\n")
        recipes.forEach {
            // TODO: The official format of the recipe must be displayed.
            println(it)
        }
    }

}