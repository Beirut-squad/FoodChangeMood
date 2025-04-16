package org.example.ui

import org.example.logic.Validator
import org.example.logic.use_case.GymHelperUseCase
import org.example.model.Recipe

class FoodChangeMoodUi(
    private val gymHelperUseCase: GymHelperUseCase,
    private val validator: Validator
) {
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