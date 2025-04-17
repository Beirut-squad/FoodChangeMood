package org.example.ui

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.SweetWithNoEggsUseCase
import org.example.logic.RandomTenRecipesIncludePotatoUseCase


class FoodChangeMoodUi(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val sweetWithNoEggs: SweetWithNoEggsUseCase,
    private val randomTenRecipesIncludePotatoUseCase: RandomTenRecipesIncludePotatoUseCase
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
                12 -> launchRandomTenPotatoUseCase()
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
        println("12- I love potato ")
        println("0- Enter 0 to exit the app")
    }

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

    private fun launchSweetWithoutEggsUseCase() {
        while (true) {
            val suggestion = sweetWithNoEggs.findSweetsFreeEggs()
            printSweetWithNoEggs()
            val choice = readln().toIntOrNull()
            when(choice) {
                1 -> suggestion?.let { println("$it") }
                0 -> break
                else -> printSweetWithNoEggs()
                }
            }
        }
    private fun printSweetWithNoEggs(){
        val suggestion = sweetWithNoEggs.findSweetsFreeEggs()
        println("Suggested Sweet: ${suggestion?.name}")
        println("Description: ${suggestion?.description}")
        println("If you like this sweet, enter 1.")
        println("If you want to see another sweet, enter anything else:")
        println("If you want to go out press 0. ")
    }

    private fun launchRandomTenPotatoUseCase()
    {
        val potatoMeals= randomTenRecipesIncludePotatoUseCase.findPotatoMeals()
        potatoMeals.forEach {
            println("$it")
        }
    }
}
