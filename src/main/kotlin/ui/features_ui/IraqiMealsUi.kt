package org.example.ui.features_ui

import org.example.logic.use_case.IraqiMealsUseCase
import org.example.ui.Viewer
import org.example.utils.Colors

class IraqiMealsUi(
    private val iraqiMealsUseCase: IraqiMealsUseCase,
    private val viewer: Viewer
) {
    fun show() {
        viewer.printTitle(
            """
            ==================================
            |      Traditional Iraqi Meals    |
            ==================================
           """.trimIndent()
        )
        iraqiMealsUseCase.getIraqiMeals().forEachIndexed { index, recipe ->
            viewer.printCorrectOutput("${index + 1}. ${recipe.name} - ${recipe.minutes} min - ${recipe.ingredients} ingredients ")
        }
    }
}