package org.example.ui.features_ui

import org.example.error.NoRecipesFoundForTheGivenDateException
import org.example.error.RecipeNotFoundException
import org.example.logic.use_case.SearchRecipeByDateUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.Viewer
import ui.Display
import utils.checkDateFormat
import utils.toDate
import java.text.ParseException
import java.time.format.DateTimeParseException

class SearchRecipeByDateUi(
    private val searchRecipeByDateUseCase: SearchRecipeByDateUseCase,
    private val reader: Reader,
    private val viewer: Viewer
) : Display{
    private var isRunning = true
    override fun show() {
        viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
        try {
            reader.readInput()?.let { inputDate ->
                inputDate.checkDateFormat()
                inputDate.toDate()
                searchRecipeByDateUseCase.searchRecipeByDate(inputDate).forEach { idAndName ->
                    viewer.printCorrectOutput("ID = ${idAndName.first} Recipe Name: ${idAndName.second}")
                }
                askUserIfHeWantDetailsOfRecipe()
            } ?: viewer.printError("Please enter a valid date")
        }catch (parseException: ParseException) {
            viewer.printError("Incorrect date format ,Please enter a valid date")
        }catch (dateTimeException: DateTimeParseException) {
            viewer.printError("Incorrect date format ,Please enter a valid date")
        }catch (noRecipesFoundForTheGivenDateException: NoRecipesFoundForTheGivenDateException) {
            println(noRecipesFoundForTheGivenDateException.message)
        }
    }


    private fun askUserIfHeWantDetailsOfRecipe() {
        isRunning = false
        viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
        reader.readInput()?.lowercase().let { answer ->
            when (answer) {
                "y" -> {
                    searchRecipeByID()
                }

                "n" -> {
                    isRunning = true
                }

                else -> {
                    viewer.printError("Invalid choice")
                    askUserIfHeWantDetailsOfRecipe()
                }
            }
        }
    }


    private fun searchRecipeByID() {
        try {
            viewer.printInfoLine("Enter the ID of the recipe whose details you want to see:")
            reader.readInput()?.let { enteredID ->
                val idAsNumber = enteredID.toIntOrNull() ?: 0
                if (idAsNumber == 0) {
                    viewer.printError("Enter valid id !")
                    askToBackToMainMenu()
                } else {
                    val recipe = searchRecipeByDateUseCase.viewDetailsOfRecipeByID(enteredID)
                    printRecipe(recipe)
                    isRunning = true
                }
            } ?: run {
                viewer.printError("Enter valid id !")
                askToBackToMainMenu()
            }
        } catch (e: RecipeNotFoundException) {
            // ask to exit from this menu to main menu
            println(e.message)
            askToBackToMainMenu()
        }
    }

    private fun askToBackToMainMenu() {
        viewer.printInfoLine("Are you need to back to main menu ? (Y/N)")
        reader.readInput()?.lowercase().let { answer ->
            when (answer) {
                "y" -> {
                    isRunning = true
                }

                "n" -> {
                    searchRecipeByID()
                }

                else -> {
                    viewer.printError("Invalid choice")
                }
            }
        }
    }

    private fun printRecipe(recipe: Recipe) {
        viewer.printCorrectOutput(
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