package org.example.ui

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.ItalianGroupMealsUseCase


class FoodChangeMoodUi(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val italianGroupMealsUseCase: ItalianGroupMealsUseCase,
) {
    fun start() {
        showWelcomeMessage()
        presentAvailableFeatures()
    }

    private fun presentAvailableFeatures() {
        var isRunning = true
        while (isRunning) {
            showOptions()
            val input = getUserInput()
            when (input) {
                4 -> launchEasyFoodSuggestionUseCase()
                15 -> launchItalianGroupMealsUseCase()
                0 -> {
                    println("Goodbye :)")
                    isRunning = false
                }

                else -> println("Invalid input, try again")
            }
        }
    }

    private fun showWelcomeMessage() {
        println("Welcome to Food Change Mood App")
    }

    private fun showOptions() {
        println("\n=== Please enter the number of the service you want: ")
        println("4- Easy Food Suggestion ")
        println("15- Italian Group Meals ")
        println("0- Enter 0 to exit the app")
    }

    private fun launchExampleUseCase() {}
    private fun launchEasyFoodSuggestionUseCase() {
        easyFoodSuggestionUseCase
            .getTenEasyFoodSuggestions()
            .forEachIndexed { index, recipe ->
                println("${index + 1}. ${recipe.name} - ${recipe.minutes} min - ${recipe.ingredients?.size} ingredients - ${recipe.steps?.size} steps")

            }
    }

    private fun launchItalianGroupMealsUseCase() {
        italianGroupMealsUseCase
            .getItalianGroupMeals()
            .forEachIndexed { index, recipe ->
                println("${index + 1}. ${recipe.name} ")

            }

    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

}



