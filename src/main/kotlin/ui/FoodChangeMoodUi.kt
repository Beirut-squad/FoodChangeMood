package org.example.ui

import org.example.logic.GetHealthyRecipesUseCase

class FoodChangeMoodUi(
    private val getHealthyRecipesUseCase: GetHealthyRecipesUseCase
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
                1 -> launchHealthyRecipes()
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
        println("1- Get names of the most healthy meals")
        println("0- Enter 0 to exit the app")
    }

    private fun launchHealthyRecipes() {
        println("Enter the number of meals you want")
        getUserInput()?.let { count ->
            getHealthyRecipesUseCase.getHealthyRecipes(count).forEach {
                println(it)
            }
        } ?: println("Invalid input :(")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}
