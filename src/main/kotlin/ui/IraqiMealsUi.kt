package org.example.ui

import org.example.logic.IraqiMealsUseCase

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