package org.example.ui

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.SweetWithNoEggsUseCase
import org.example.model.Recipe


class FoodChangeMoodUi(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val sweetWithNoEggs: SweetWithNoEggsUseCase
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
                6 -> launchSweetWithoutEggsUseCase()
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
        println("6- Sweets with no eggs")
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

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

    private fun launchSweetWithoutEggsUseCase(): Recipe? {
        while (true) {
            val suggestion = sweetWithNoEggs.findSweetsFreeEggs().firstOrNull()

            if (suggestion == null) {
                println("Sorry, no egg-free sweets found.")
                return null
            }

            println("Suggested Sweet: ${suggestion.name}")
            println("Description: ${suggestion.description}")
            println("If you like this sweet, enter 1.")
            println("If you want to see another sweet, enter anything else:")

            val choice = readln().toIntOrNull()
            if (choice == 1) {
                println("Yay! You selected: ${suggestion.name}")
                return suggestion
            }
        }
    }
}
