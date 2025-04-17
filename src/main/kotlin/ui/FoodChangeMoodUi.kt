package org.example.ui

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.ItalianGroupMealsUseCase
import org.example.logic.RandomTenRecipesIncludePotatoUseCase
import org.example.model.Recipe


class FoodChangeMoodUi(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase
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
                12 -> launchRandomTenPotatoUseCase()
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
        println("12- I love potato ")
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

fun launchGymHelperUi() {
    println("Gym helper: Get meals that match the protein and calories amounts you choose or close to them.")
    while (true) {
        print("Enter the amount of protein: ")
        val protein = readlnOrNull()

        print("Enter the amount of calories: ")
        val calories = readlnOrNull()

        if (validator.validateGymHelperInput(calories, protein)) {
            val recipes =  gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(
                calories = calories?.toFloat() ?: 0f,
                protein = protein?.toFloat() ?: 0f
            )
            displayRecipesForGymHelper(recipes)
            break
        } else {
            println("Invalid input.")
        }
    }


}

private fun displayRecipesForGymHelper(recipes: List<Recipe>) {
    recipes.forEachIndexed { index, recipe ->
        displaySingleRecipeForGymHelper(recipe, index + 1)
        println()
    }
}

private fun displaySingleRecipeForGymHelper(recipe: Recipe, index: Int) {
    println("Meal $index: ${recipe.name}")

    println("Calories: ${recipe.nutrition?.calories ?: 0.0}, Protein: ${recipe.nutrition?.protein ?: 0.0}")

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

private fun launchRandomTenPotatoUseCase()
{
    val potatoMeals= randomTenRecipesIncludePotatoUseCase.findPotatoMeals()
    potatoMeals.forEach {
        println("\t\t $it")
    }
}
}
