package org.example.ui.features_ui

import org.example.logic.use_case.IraqiMealsUseCase
import org.example.utils.Colors

class IraqiMealsUi(
    private val iraqiMealsUseCase: IraqiMealsUseCase,
    private val colors: Colors
) {
    fun show() {
        println(
            colors.cyan(
                """
            ==================================
            |      Traditional Iraqi Meals    |
            ==================================
           """.trimIndent()
            )
        )
        iraqiMealsUseCase.getIraqiMeals().forEachIndexed { index, recipe ->
            println(colors.green("${index + 1}. ${recipe.name} - ${recipe.minutes} min - ${recipe.ingredients} ingredients "))
        }
    }
}