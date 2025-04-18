package org.example.ui

import org.example.logic.EasyFoodSuggestionUseCase

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