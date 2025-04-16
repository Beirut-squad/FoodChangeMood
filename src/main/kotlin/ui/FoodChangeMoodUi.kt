package org.example.ui

import org.example.logic.KetoDiet
import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.RandomTenRecipesIncludePotatoUseCase
import org.example.model.Nutrition
import org.example.model.Recipe


class FoodChangeMoodUi(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val randomTenRecipesIncludePotatoUseCase: RandomTenRecipesIncludePotatoUseCase,
    private val ketoDiet: KetoDiet,
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
                7 -> launchKetoDietUseCase()
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
        println("7- Keto Diet Food Suggestion ")
        println("12- I love potato ")
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

    private fun launchKetoDietUseCase(){
//        val ketoMealsList = ketoDiet.getSortedKetoRecipesDescending()
        println("Welcome to Keto Meal Suggester ")
        while (true){
            println("1. Suggest a Keto Recipe \n2. Go Back ")
            val input: String? = readlnOrNull()
            when(input){
                "1" -> {
                    val recipe = ketoDiet.suggestKetoRecipe()
                    println("Meal name: ${recipe.name}")
                    println("Do you want to proceed with Recipe details ? (Y,n) ")
                    val input: String? = readlnOrNull()
                    if (input == "Y"){
                        printRecipeDetails(recipe)
                    }
                    continue
                }
                "2" -> break
                else -> {
                    println("enter a valid number")
                }
            }

        }


    }

    private fun printRecipeDetails(recipe: Recipe) {
        println("=== Recipe Details ===")
        printBasicInfo(recipe)
        println()
        printTags(recipe.tags)
        println()
        printNutritionInfo(recipe.nutrition)
        println()
        printSteps(recipe.steps, recipe.numberOfSteps)
        println()
        printDescription(recipe.description)
        println()
        printIngredients(recipe.ingredients, recipe.numberOfIngredients)
        println()
    }

    private fun printBasicInfo(recipe: Recipe) {
        println("Name: ${recipe.name ?: "N/A"}")
        println("ID: ${recipe.id ?: "N/A"}")
        println("Preparation Time: ${recipe.minutes ?: "N/A"} minutes")
        println("Contributor ID: ${recipe.contributorId ?: "N/A"}")
        println("Submitted Date: ${recipe.submittedDate ?: "N/A"}")
    }

    private fun printTags(tags: List<String>?) {
        println("Tags: ${tags?.joinToString(", ") ?: "None"}")
    }

    private fun printNutritionInfo(nutrition: Nutrition?) {
        println("--- Nutrition Information ---")
        nutrition?.let {
            println("Calories: ${it.calories ?: "N/A"}")
            println("Total Fat: ${it.totalFat ?: "N/A"} g")
            println("Sugar: ${it.sugar ?: "N/A"} g")
            println("Sodium: ${it.sodium ?: "N/A"} mg")
            println("Protein: ${it.protein ?: "N/A"} g")
            println("Saturated Fat: ${it.saturatedFat ?: "N/A"} g")
            println("Carbohydrates: ${it.carbohydrates ?: "N/A"} g")
        } ?: println("Nutrition Info: N/A")
    }

    private fun printSteps(steps: List<String>?, numberOfSteps: Int?) {
        println("Number of Steps: ${numberOfSteps ?: "N/A"}")
        println("--- Steps ---")
        steps?.forEachIndexed { index, step ->
            println("${index + 1}. $step")
        } ?: println("None")
    }

    private fun printDescription(description: String?) {
        println("Description: ${description ?: "N/A"}")
    }

    private fun printIngredients(ingredients: List<String>?, numberOfIngredients: Int?) {
        println("--- Ingredients ---")
        println("Number of Ingredients: ${numberOfIngredients ?: "N/A"}")
        ingredients?.forEachIndexed { index, ingredient ->
            println("${index + 1}. $ingredient")
        } ?: println("None")

    }


}
