package org.example.ui.features_ui

import org.example.logic.Validator
import org.example.logic.use_case.HealthyRecipesUseCase
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.utils.Colors

class HealthyFoodRecipesUi(
    private val validator: Validator,
    private val healthyRecipesUseCase: HealthyRecipesUseCase,
    private val colors: Colors,
    private val viewer: Viewer

) {
    fun show() {
//        outputPrinter(colors.cyan("Enter the number of meals you want"))
        viewer.printTitle("Enter the number of meals you want")
        val count = getUserInput()

        if (count == null || !validator.validateRecipesCountInput(count)) {
//            outputPrinter(colors.red("Invalid Input, Enter a Positive Number"))
            viewer.printError("Invalid Input, Enter a Positive Number")
        } else
            displayRecipeInfo(count)
    }

    fun outputPrinter(message: String){
        println(message)
    }

    fun displayRecipeInfo(count: Int) {
//        outputPrinter(colors.blue("Loading..."))
        viewer.printLoader("Loading...")
        val healthyRecipes = healthyRecipesUseCase.getHealthyRecipes(count)
        if (healthyRecipes.isEmpty()) {
//            outputPrinter(colors.red("No Recipes Available"))
            viewer.printError("No Recipes Available")
        } else healthyRecipes.forEach {
//            outputPrinter(RecipeFormatter.format(it))
            viewer.printPlainText(RecipeFormatter.format(it))
        }
    }

     fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

}