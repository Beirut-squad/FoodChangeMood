package org.example.ui.features_ui

import org.example.error.RecipeNotFoundException
import org.example.logic.use_case.SeafoodWithHighProteinUseCase
import org.example.utils.Colors

class SeafoodWithHighProteinUi(
    private val seafoodWithHighProteinUseCase: SeafoodWithHighProteinUseCase,
    private val colors: Colors
) {
    fun show() {
        try {
            println(colors.blue("Loading..."))
            val recipes = seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()
            recipes.forEachIndexed { index, recipe ->
                println(
                    colors.green(
                        "${index + 1}. " +
                                "Recipe Name: \n\t${recipe.name} " +
                                "\n\tProtein Amount: \n\t${recipe.nutrition?.protein}"
                    )
                )
            }
        } catch (e: RecipeNotFoundException) {
            println(colors.red("Error: ${e.message}"))
        }
    }
}
