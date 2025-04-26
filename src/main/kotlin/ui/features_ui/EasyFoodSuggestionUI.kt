package org.example.ui.features_ui

import org.example.logic.use_case.EasyFoodSuggestionUseCase
import org.example.utils.Colors
import ui.Display

class EasyFoodSuggestionUI(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val colors: Colors,
): Display {
    override fun show() {
        easyFoodSuggestionUseCase
            .getTenEasyFoodSuggestions()
            .forEachIndexed { index, recipe ->
                println(colors.green("${index + 1}. ${recipe.name} - ${recipe.minutes} min - " +
                        "${recipe.ingredients?.size ?: 0} ingredients - ${recipe.steps?.size ?: 0} steps"))
            }
    }
}