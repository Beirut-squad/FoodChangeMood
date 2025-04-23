package org.example.ui.features_ui

import org.example.logic.Validator
import org.example.logic.use_case.GymHelperUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.Viewer
import org.example.utils.Colors

class GymHelperUi (
    private val gymHelperUseCase: GymHelperUseCase,
    private val validator: Validator,
    private val colors: Colors,
    private val viewer: Viewer,
    private val reader: Reader

    ){
    fun show() {
        viewer.printOutputWithNewLine(colors.cyan("Gym helper: Get meals that match the protein and calories amounts you choose or close to them."))
        while (true) {
            viewer.printOutput(colors.blue("Enter the amount of protein: "))
            val protein = reader.readInput()

            viewer.printOutput(colors.blue("Enter the amount of calories: "))
            val calories = reader.readInput()

            if (validator.validateGymHelperInput(calories, protein)) {
                val recipes =  gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(
                    calories = calories?.toFloat() ?: 0f,
                    protein = protein?.toFloat() ?: 0f
                )
                displayRecipesForGymHelper(recipes)
                break
            } else {
                viewer.printOutputWithNewLine(colors.red("Invalid input."))
            }
        }


    }

    private fun displayRecipesForGymHelper(recipes: List<Recipe>) {
        recipes.forEachIndexed { index, recipe ->
            displaySingleRecipeForGymHelper(recipe, index + 1)
            viewer.printOutputWithNewLine("")
        }
    }

    private fun displaySingleRecipeForGymHelper(recipe: Recipe, index: Int) {
        viewer.printOutputWithNewLine(colors.green("Meal $index: ${recipe.name}"))

        viewer.printOutputWithNewLine(colors.green("Calories: ${recipe.nutrition?.calories ?: 0.0}, Protein: ${recipe.nutrition?.protein ?: 0.0}"))

        recipe.ingredients?.let { displayIngredients(recipe.ingredients) }

        recipe.steps?.let { displaySteps(recipe.steps) }
    }

    private fun displayIngredients(ingredients: List<String>) {
        viewer.printOutput(colors.green("Ingredients: "))
        ingredients.forEach {
            viewer.printOutput(colors.green("$it, "))
        }
    }

    private fun displaySteps(steps: List<String>) {
        viewer.printOutputWithNewLine(colors.green("How to Make: "))
        steps.forEachIndexed { stepIndex, step ->
            viewer.printOutput(colors.green("Step ${stepIndex + 1}: "))
            viewer.printOutputWithNewLine(step)
        }
    }

}