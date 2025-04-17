package org.example.logic

import org.example.model.Recipe

class EasyFoodSuggestionUseCase(
    private val repository: RecipesRepository
) {
    fun getTenEasyFoodSuggestions(): List<Recipe> {
        return repository.getAllRecipes()
            .filter {
                (it.minutes?: Int.MAX_VALUE) <= MAX_EASY_MINUTES &&
                        (it.ingredients?.size?: Int.MAX_VALUE) <= MAX_INGREDIENTS &&
                        (it.steps?.size?: Int.MAX_VALUE) <= MAX_STEPS
            }
            .shuffled()
            .take(SUGGESTION_COUNT)
    }
    companion object {
        private const val MAX_EASY_MINUTES = 30
        private const val MAX_INGREDIENTS = 5
        private const val MAX_STEPS = 6
        private const val SUGGESTION_COUNT = 10
    }

}