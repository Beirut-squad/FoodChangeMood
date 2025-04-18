package org.example.ui.features_ui

import org.example.logic.use_case.SeafoodWithHighProteinUseCase
import org.example.utils.Colors

class SeafoodWithHighProteinUi (
    private val seafoodWithHighProteinUseCase: SeafoodWithHighProteinUseCase,
    private val colors: Colors

    ){
     fun show() {
        println(colors.blue("Loading..."))
        seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()
            .forEachIndexed { index, recipe ->
                println(
                    colors.green("${index + 1}. " +
                            "Recipe Name: \n\t${recipe.name} " +
                            "\n\tProtein Amount: \n\t${recipe.nutrition?.protein}"
                ))
            }
    }
}