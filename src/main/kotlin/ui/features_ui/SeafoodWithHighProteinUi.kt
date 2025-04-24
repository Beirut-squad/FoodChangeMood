package org.example.ui.features_ui

import org.example.error.RecipeNotFoundException
import org.example.logic.use_case.SeafoodWithHighProteinUseCase
import org.example.ui.Viewer
import org.example.utils.Colors

class SeafoodWithHighProteinUi(
    private val seafoodWithHighProteinUseCase: SeafoodWithHighProteinUseCase,
    private val viewer: Viewer
) {
    fun show() {
        try {
            displayPrint()
        } catch (e: RecipeNotFoundException) {
            viewer.printError("Error: ${e.message}")
        }
    }
    private fun displayPrint() {
        viewer.printLoader("Loading...")
        val recipes = seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()
        recipes.forEachIndexed { index, recipe ->
            viewer.printCorrectOutput(
                "${index + 1}. Recipe Name: ${recipe.name} \n\tProtein Amount: \n\t${recipe.nutrition?.protein}"
            )
        }
    }
}
