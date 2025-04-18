package org.example.ui.features_ui

import Utils.checkDateFormat
import org.example.error.NoRecipesFoundForTheGivenDateException
import org.example.error.RecipeNotFoundException
import org.example.logic.use_case.SearchRecipeByDateUseCase
import org.example.model.Recipe
import java.text.ParseException
import java.time.format.DateTimeParseException

class SearchRecipeByDateUi(
    private val searchRecipeByDateUseCase: SearchRecipeByDateUseCase,

    ) {
    var isRunning = true
     fun show() {
        println("Enter the date for which you want to view recipes: example (2006-10-07)")
        try {
            readlnOrNull()?.let { inputDate ->
                inputDate.checkDateFormat()
                searchRecipeByDateUseCase.searchRecipeByDate(inputDate).forEach { idAndName ->
                    println("ID = ${idAndName.first} Recipe Name: ${idAndName.second}")
                }
                askUserIfHeWantDetailsOfRecipe()
            } ?: println("Please enter a valid date")
        } catch (parseException: ParseException) {
            println("Incorrect date format ,Please enter a valid date")
        } catch (dateTimeException: DateTimeParseException) {
            println("Incorrect date format ,Please enter a valid date")
        } catch (noRecipesFoundForTheGivenDateException: NoRecipesFoundForTheGivenDateException) {
            println(noRecipesFoundForTheGivenDateException.message)
        }
    }


    private fun askUserIfHeWantDetailsOfRecipe() {
        isRunning = false
        println("Do you want to get details of a specific recipe? (Y/N)")
        readlnOrNull()?.lowercase().let { answer ->
            when (answer) {
                "y" -> {
                    searchRecipeByID()
                }

                "n" -> {
                    isRunning = true
                }

                else -> {
                    println("Invalid choice")
                    askUserIfHeWantDetailsOfRecipe()
                }
            }
        }
    }

    private fun searchRecipeByID() {
        try {
            println("Enter the ID of the recipe whose details you want to see:")
            readlnOrNull()?.let { enteredID ->
                val idAsNumber = enteredID.toIntOrNull() ?: 0
                if (idAsNumber == 0) {
                    println("Enter valid id !")
                    askToBackToMainMenu()
                } else {
                    val recipe = searchRecipeByDateUseCase.viewDetailsOfRecipeByID(enteredID)
                    printRecipe(recipe)
                    isRunning = true
                }
            } ?: {
                println("Enter valid id !")
                askToBackToMainMenu()
            }
        } catch (e: RecipeNotFoundException) {
            // ask to exit from this menu to main menu
            println(e.message)
            askToBackToMainMenu()
        }
    }

    private fun askToBackToMainMenu() {
        println("Are you need to back to main menu ? (Y/N)")
        readlnOrNull()?.lowercase().let { answer ->
            when (answer) {
                "y" -> {
                    isRunning = true
                }

                "n" -> {
                    searchRecipeByID()
                }

                else -> {
                    println("Invalid choice")
                }
            }
        }
    }

    private fun printRecipe(recipe: Recipe) {
        println(
            "Recipe Details: ------------------------------------------------\nName: ${recipe.name}\n" +
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
        )
    }


}