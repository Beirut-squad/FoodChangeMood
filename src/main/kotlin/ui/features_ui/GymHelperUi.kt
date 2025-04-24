package org.example.ui.features_ui

import org.example.logic.Validator
import org.example.logic.use_case.GymHelperUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.Viewer
import ui.Display

class GymHelperUi(
    private val gymHelperUseCase: GymHelperUseCase,
    private val validator: Validator,
    private val viewer: Viewer,
    private val reader: Reader

) : Display {
    override fun show() {

        viewer.printTitle("Gym helper: Get meals that match the protein and calories amounts you choose or close to them.")
        while (true) {
            viewer.printLoader("Enter the amount of protein: ", false)
            val protein = reader.readInput()

            viewer.printLoader("Enter the amount of calories: ", false)
            val calories = reader.readInput()

            if (validator.validateGymHelperInput(calories, protein)) {
                val recipes = gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(
                    calories = calories?.toFloat() ?: 0f,
                    protein = protein?.toFloat() ?: 0f
                )
                displayRecipesForGymHelper(recipes)
                break
            } else {
                viewer.printError("Invalid input.")
            }
        }


    }

    private fun displayRecipesForGymHelper(recipes: List<Recipe>) {
        recipes.forEachIndexed { index, recipe ->
            displaySingleRecipeForGymHelper(recipe, index + 1)
            viewer.printPlainText("")
        }
    }

    private fun displaySingleRecipeForGymHelper(recipe: Recipe, index: Int) {
        viewer.printCorrectOutput("Meal $index: ${recipe.name}")

        viewer.printCorrectOutput("Calories: ${recipe.nutrition?.calories ?: 0.0}, Protein: ${recipe.nutrition?.protein ?: 0.0}")

        recipe.ingredients?.let { displayIngredients(recipe.ingredients) }

        recipe.steps?.let { displaySteps(recipe.steps) }
    }

    private fun displayIngredients(ingredients: List<String>) {
        viewer.printCorrectOutput("Ingredients: ", false)
        ingredients.forEach {
            viewer.printCorrectOutput("$it, ", false)
        }
    }

    private fun displaySteps(steps: List<String>) {
        viewer.printCorrectOutput("How to Make: ")
        steps.forEachIndexed { stepIndex, step ->
            viewer.printCorrectOutput("Step ${stepIndex + 1}: ", false)
            viewer.printPlainText(step)
        }
    }

}