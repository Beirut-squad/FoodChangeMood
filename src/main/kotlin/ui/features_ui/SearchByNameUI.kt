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
        println(colors.cyan("Enter the name of the dish or part of it to search for:"))
        val userInput = nameToSearch.nextLine()

        try {
            //search in input by fun searchByNameUseCase
            val recipe: Recipe? = searchByNameUseCase.searchRecipeByName(userInput)
            if (recipe != null) { // found recipe
                println(colors.blue("\n-------------------------------\n"))
                println(colors.green("Found the recipe: ${recipe.name}"))
                println(colors.blue("-------------------------------\n"))
                printRecipe(recipe)  // print repice's contants
            } else {
                println(colors.red("\nSorry, we couldn't find a recipe that matches the name you entered."))
            }

        } catch (e: ThereIsNoNameException) {
            println(colors.red("\nAn error occurred while searching: ${e.message}"))
        }
    }

    private fun printRecipe(recipe: Recipe) {
        println(
            colors.green("Recipe Details: ------------------------------------------------\nName: ${recipe.name}\n" +
                    "Minutes: ${recipe.minutes}\nContributor Id: ${recipe.contributorId}\n" +
                    "Submitted Date: ${recipe.submittedDate}\nTags:\n${recipe.tags}\n" +
                    "Nutrition:\nCalories = ${recipe.nutrition?.calories}\t" +
                    "Total Fat = ${recipe.nutrition?.totalFat}\t" +
                    "Sugar = ${recipe.nutrition?.sugar}\t" +
                    "Sodium = ${recipe.nutrition?.sodium}\t" +
                    "Protein = ${recipe.nutrition?.protein}\t" +
                    "Saturated Fat = ${recipe.nutrition?.saturatedFat}\t" +
                    "Carbohydrates = ${recipe.nutrition?.carbohydrates}\n" +
                    "Number Of Steps: ${recipe.numberOfSteps}\n" +
                    "Steps:\n${recipe.steps}\n" +
                    "Description: ${recipe.description}\n" +
                    "Ingredients:\n${recipe.ingredients}\n" +
                    "Number Of Ingredients: ${recipe.numberOfIngredients}"
        ))
    }
}
