package org.example.ui.features_ui

import org.example.error.NoRecipesFoundForTheGivenDateException
import org.example.error.RecipeNotFoundException
import org.example.logic.use_case.SearchRecipeByDateUseCase
import org.example.model.Recipe
import org.example.utils.Colors
import utils.checkDateFormat
import java.text.ParseException
import java.time.format.DateTimeParseException

class SearchRecipeByDateUi(
    private val searchRecipeByDateUseCase: SearchRecipeByDateUseCase,
    private val colors: Colors
) {
    private var isRunning = true
    fun show() {
        println(colors.cyan("Enter the date for which you want to view recipes: example (2006-10-07)"))
        try {
            readlnOrNull()?.let { inputDate ->
                inputDate.checkDateFormat()
                searchRecipeByDateUseCase.searchRecipeByDate(inputDate).forEach { idAndName ->
                    println(colors.green("ID = ${idAndName.first} Recipe Name: ${idAndName.second}"))
                }
                askUserIfHeWantDetailsOfRecipe()
            } ?: println(colors.red("Please enter a valid date"))
        } catch (parseException: ParseException) {
            println(colors.red("Incorrect date format ,Please enter a valid date"))
        } catch (dateTimeException: DateTimeParseException) {
            println(colors.red("Incorrect date format ,Please enter a valid date"))
        } catch (noRecipesFoundForTheGivenDateException: NoRecipesFoundForTheGivenDateException) {
            println(noRecipesFoundForTheGivenDateException.message)
        }
    }


    private fun askUserIfHeWantDetailsOfRecipe() {
        isRunning = false
        println(colors.yellow("Do you want to get details of a specific recipe? (Y/N)"))
        readlnOrNull()?.lowercase().let { answer ->
            when (answer) {
                "y" -> {
                    searchRecipeByID()
                }

                "n" -> {
                    isRunning = true
                }

                else -> {
                    println(colors.red("Invalid choice"))
                    askUserIfHeWantDetailsOfRecipe()
                }
            }
        }
    }

    private fun searchRecipeByID() {
        try {
            println(colors.yellow("Enter the ID of the recipe whose details you want to see:"))
            readlnOrNull()?.let { enteredID ->
                val idAsNumber = enteredID.toIntOrNull() ?: 0
                if (idAsNumber == 0) {
                    println(colors.red("Enter valid id !"))
                    askToBackToMainMenu()
                } else {
                    val recipe = searchRecipeByDateUseCase.viewDetailsOfRecipeByID(enteredID)
                    printRecipe(recipe)
                    isRunning = true
                }
            } ?: {
                println(colors.red("Enter valid id !"))
                askToBackToMainMenu()
            }
        } catch (e: RecipeNotFoundException) {
            // ask to exit from this menu to main menu
            println(e.message)
            askToBackToMainMenu()
        }
    }

    private fun askToBackToMainMenu() {
        println(colors.yellow("Are you need to back to main menu ? (Y/N)"))
        readlnOrNull()?.lowercase().let { answer ->
            when (answer) {
                "y" -> {
                    isRunning = true
                }

                "n" -> {
                    searchRecipeByID()
                }

                else -> {
                    println(colors.red("Invalid choice"))
                }
            }
        }
    }

    private fun printRecipe(recipe: Recipe) {
        println(
            colors.green(
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
        )
    }


}