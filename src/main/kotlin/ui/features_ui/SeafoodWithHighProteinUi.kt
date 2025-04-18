package org.example.ui.features_ui

import org.example.logic.use_case.SeafoodWithHighProteinUseCase

class SeafoodWithHighProteinUi (
    private val seafoodWithHighProteinUseCase: SeafoodWithHighProteinUseCase,

    ){
     fun show() {
        println("Loading...")
        seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()
            .forEachIndexed { index, recipe ->
                println(
                    "${index + 1}. " +
                            "Recipe Name: \n\t${recipe.name} " +
                            "\n\tProtein Amount: \n\t${recipe.nutrition?.protein}"
                )
            }
    }
}