package org.example.ui

import org.example.logic.IraqiMealsUseCase

class FoodChangeMoodUi(private val iraqiMealsUseCase: IraqiMealsUseCase) {

    private fun presentIraqMeals() {
        println(
            """
            ==================================
            |      Traditional Iraqi Meals    |
            ==================================
           """.trimIndent()
        )
        iraqiMealsUseCase.getIraqiMeals().forEach {
            println("• $it")
        }
    }
}