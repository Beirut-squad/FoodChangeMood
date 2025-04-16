package org.example.ui

import org.example.logic.Validator
import org.example.logic.use_case.GymHelperUseCase
import org.example.model.Recipe
import org.example.logic.EasyFoodSuggestionUseCase


class FoodChangeMoodUi(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val gymHelperUseCase: GymHelperUseCase,
    private val validator: Validator
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
    
    fun launchGymHelperUi() {
        println("Gym helper: Get meals that match the protein and calories amounts you choose or close to them.")
        while (true) {
            print("Enter the amount of protein: ")
            val protein = readlnOrNull()

            print("Enter the amount of calories: ")
            val calories = readlnOrNull()

            if (validator.validateGymHelperInput(calories, protein)) {
                val recipes = getRecipesForGymHelper(calories!!, protein!!)
                displayRecipesForGymHelper(recipes)
                break
            } else {
                println("Invalid input.")
            }
        }


    }

    private fun getRecipesForGymHelper(calories: String, protein: String): List<Recipe> {
        return gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(
            calories = calories.toFloat(),
            protein = protein.toFloat()
        )
    }

    private fun displayRecipesForGymHelper(recipes: List<Recipe>) {
        recipes.forEachIndexed { index, recipe ->
            displaySingleRecipeForGymHelper(recipe, index + 1)
            println()
        }
    }

    private fun displaySingleRecipeForGymHelper(recipe: Recipe, index: Int) {
        println("Meal $index: ${recipe.name}")

        println("Calories: ${recipe.nutrition.calories}, Protein: ${recipe.nutrition.protein}")

        recipe.ingredients?.let { displayIngredients(recipe.ingredients) }

        recipe.steps?.let { displaySteps(recipe.steps) }
    }

    private fun displayIngredients(ingredients: List<String>) {
        print("Ingredients: ")
        ingredients.forEach {
            print("$it, ")
        }
    }

    private fun displaySteps(steps: List<String>) {
        println("How to Make: ")
        steps.forEachIndexed { stepIndex, step ->
            print("Step ${stepIndex + 1}: ")
            println(step)
        }
    }
}