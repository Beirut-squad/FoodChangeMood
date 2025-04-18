package org.example.ui.features_ui

import org.example.logic.use_case.EasyFoodSuggestionUseCase
import org.example.utils.Colors

class EasyFoodSuggestionUI(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val colors: Colors
) {
     fun show() {
        easyFoodSuggestionUseCase
            .getTenEasyFoodSuggestions()
            .forEachIndexed { index, recipe ->
                println(colors.green("${index + 1}. ${recipe.name} - ${recipe.minutes} min - ${recipe.ingredients?.size} ingredients - ${recipe.steps?.size} steps"))

            }
    }
}