package org.example.ui

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.RandomTenRecipesIncludePotatoUseCase
import org.example.logic.ThinProblemUseCase
import org.example.model.Recipe


class FoodChangeMoodUi(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val randomTenRecipesIncludePotatoUseCase: RandomTenRecipesIncludePotatoUseCase,
    private val thinProblem: ThinProblemUseCase
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
                12 -> launchRandomTenPotatoUseCase()
                13 -> launchThinProblemUseCase()
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
        println("12- I love potato ")
        println("13- Thin problem Suggestion ")
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

    private fun launchRandomTenPotatoUseCase()
    {
        val potatoMeals= randomTenRecipesIncludePotatoUseCase.findPotatoMeals()
        potatoMeals.forEach {
            println("\t\t $it")
        }
    }
    private fun launchThinProblemUseCase() {

        while (true) {
            val suggestion = thinProblem.findThinProblem()
            printThinProblem()
            val choice = readln().toIntOrNull()
            if (choice == 1) {
                suggestion?.let { println("\t\t $it") }
            } else break
        }
    }

    private fun printThinProblem(){
        val suggestion = thinProblem.findThinProblem()
        println("Suggested Meal: ${suggestion?.name}")
        println("Description: ${suggestion?.description}")
        println("Calories: ${suggestion?.nutrition?.calories}")
        println("Like it? Enter 1")
        println("Want another? Enter anything else:")
    }
}
