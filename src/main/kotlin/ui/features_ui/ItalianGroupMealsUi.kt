package org.example.ui.features_ui

import org.example.logic.use_case.ItalianGroupMealsUseCase
import org.example.utils.Colors

class ItalianGroupMealsUi(
    private val italianGroupMealsUseCase: ItalianGroupMealsUseCase,
    private val colors: Colors
) {
    fun show() {
        italianGroupMealsUseCase
            .getItalianGroupMeals()
            .forEachIndexed { index, recipe ->
                println(colors.green("${index + 1}. ${recipe.name} "))
            }
    }
}