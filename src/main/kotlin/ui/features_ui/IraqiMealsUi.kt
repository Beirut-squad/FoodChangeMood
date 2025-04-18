package org.example.ui.features_ui

import org.example.logic.use_case.IraqiMealsUseCase

class IraqiMealsUi(
    private val iraqiMealsUseCase: IraqiMealsUseCase
) {
     fun show() {
        println(
            """
            ==================================
            |      Traditional Iraqi Meals    |
            ==================================
           """.trimIndent()
        )
        iraqiMealsUseCase.getIraqiMeals().forEachIndexed { index, recipe ->
            println("${index + 1}. ${recipe.name} - ${recipe.minutes} min - ${recipe.ingredients} ingredients ")
        }
    }
}