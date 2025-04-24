package org.example.ui.features_ui

import org.example.logic.Validator
import org.example.logic.use_case.HealthyRecipesUseCase
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.utils.Colors

class HealthyFoodRecipesUi(
    private val validator: Validator,
    private val healthyRecipesUseCase: HealthyRecipesUseCase,
    private val viewer: Viewer,
    private val reader: Reader,

) {
    fun show() {
        viewer.printTitle("Enter the number of meals you want")
        val count = reader.readInt()

        if (count == null || !validator.validateRecipesCountInput(count)) {
            viewer.printError("Invalid Input, Enter a Positive Number")
        } else
            displayRecipeInfo(count.toInt())
    }

    private fun displayRecipeInfo(count: Int) {
        viewer.printLoader("Loading...")
        val healthyRecipes = healthyRecipesUseCase.getHealthyRecipes(count)
        if (healthyRecipes.isEmpty()) {
            viewer.printError("No Recipes Available")
        } else healthyRecipes.forEach {
            viewer.printPlainText(RecipeFormatter.format(it))
        }
    }
}
