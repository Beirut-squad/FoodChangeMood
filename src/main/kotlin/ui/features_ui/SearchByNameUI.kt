package org.example.ui.features_ui

import org.example.error.ThereIsNoNameException
import org.example.logic.use_case.SearchByNameUseCase
import org.example.model.Recipe
import org.example.utils.Colors
import java.util.*

class SearchByNameUI(
    private val searchByNameUseCase: SearchByNameUseCase,
    private val colors: Colors
) {
    fun show() {
        val nameToSearch = Scanner(System.`in`)
        println(colors.cyan("Enter the name of the Repice to search for:"))
        val userInput = nameToSearch.nextLine()

        try {
            // Perform search using searchByNameUseCase
            val recipes: List<Recipe>? = searchByNameUseCase.searchRecipeByName(userInput)

            if (recipes != null && recipes.isNotEmpty()) {
                println(colors.blue("\n-------------------------------\n"))
                println(colors.green("Found ${recipes.size} recipes:"))
                println(colors.blue("-------------------------------\n"))
                recipes.forEach { recipe ->
                    printRecipe(recipe)  // Print details of each recipe
                }
            } else {
                println(colors.red("\nSorry, we couldn't find any recipes that match the name you entered."))
            }

        } catch (e: ThereIsNoNameException) {
            println(colors.red("\nAn error occurred while searching: ${e.message}"))
        }
    }

    private fun printRecipe(recipe: Recipe) {
        println(
            colors.green("Recipe Details: ------------------------------------------------\nName: ${recipe.name}\n" +
                    "Minutes: ${recipe.minutes}\nContributor Id: ${recipe.contributorId}\n" +
                    "Protein = ${recipe.nutrition?.protein}\t" +
                    "Saturated Fat = ${recipe.nutrition?.saturatedFat}\t" +
                    "Carbohydrates = ${recipe.nutrition?.carbohydrates}\n" +
                    "Number Of Steps: ${recipe.numberOfSteps}\n" +
                    "Steps:\n${recipe.steps}\n" +
                    "Description: ${recipe.description}\n"
            )
        )

    }
}
