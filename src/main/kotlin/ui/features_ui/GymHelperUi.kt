package org.example.ui.features_ui

import org.example.logic.Validator
import org.example.logic.use_case.GymHelperUseCase
import org.example.model.Recipe
import org.example.utils.Colors

class GymHelperUi (
    private val gymHelperUseCase: GymHelperUseCase,
    private val validator: Validator,
    private val colors: Colors
){
    fun show() {
        println(colors.cyan("Gym helper: Get meals that match the protein and calories amounts you choose or close to them."))
        while (true) {
            print(colors.blue("Enter the amount of protein: "))
            val protein = readlnOrNull()

            print(colors.blue("Enter the amount of calories: "))
            val calories = readlnOrNull()

            if (validator.validateGymHelperInput(calories, protein)) {
                val recipes =  gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(
                    calories = calories?.toFloat() ?: 0f,
                    protein = protein?.toFloat() ?: 0f
                )
                displayRecipesForGymHelper(recipes)
                break
            } else {
                println(colors.red("Invalid input."))
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
        println(colors.green("Meal $index: ${recipe.name}"))

        println(colors.green("Calories: ${recipe.nutrition?.calories ?: 0.0}, Protein: ${recipe.nutrition?.protein ?: 0.0}"))

        recipe.ingredients?.let { displayIngredients(recipe.ingredients) }

        recipe.steps?.let { displaySteps(recipe.steps) }
    }

    private fun displayIngredients(ingredients: List<String>) {
        print(colors.green("Ingredients: "))
        ingredients.forEach {
            print(colors.green("$it, "))
        }
    }

    private fun displaySteps(steps: List<String>) {
        println(colors.green("How to Make: "))
        steps.forEachIndexed { stepIndex, step ->
            print(colors.green("Step ${stepIndex + 1}: "))
            println(step)
        }
    }

}