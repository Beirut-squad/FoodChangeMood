package org.example.ui.features_ui

import org.example.logic.use_case.EasyFoodSuggestionUseCase

class EasyFoodSuggestionUI(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase
) {
     fun show() {
        easyFoodSuggestionUseCase
            .getTenEasyFoodSuggestions()
            .forEachIndexed { index, recipe ->
                println("${index + 1}. ${recipe.name} - ${recipe.minutes} min - ${recipe.ingredients?.size} ingredients - ${recipe.steps?.size} steps")

            }
    }
}