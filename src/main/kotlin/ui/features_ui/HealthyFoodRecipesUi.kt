package org.example.ui.features_ui

import org.example.logic.Validator
import org.example.logic.use_case.HealthyRecipesUseCase
import org.example.ui.RecipeFormatter
import org.example.utils.Colors

class HealthyFoodRecipesUi(
    private val validator: Validator,
    private val healthyRecipesUseCase: HealthyRecipesUseCase,
    private val colors: Colors

) {
    fun show() {
        println(colors.cyan("Enter the number of meals you want"))
        val count = getUserInput()

        if (count == null || !validator.validateRecipesCountInput(count)) {
            println(colors.red("Invalid Input, Enter a Positive Number"))
        } else
            displayRecipeInfo(count)
    }

    private fun displayRecipeInfo(count: Int) {
        println(colors.blue("Loading..."))
        val healthyRecipes = healthyRecipesUseCase.getHealthyRecipes(count)
        if (healthyRecipes.isEmpty()) {
            println(colors.red("No Recipes Available"))
        } else healthyRecipes.forEach {
            println(RecipeFormatter.format(it))
        }
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

}