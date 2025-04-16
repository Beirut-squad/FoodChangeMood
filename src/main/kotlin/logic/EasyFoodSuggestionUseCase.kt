package org.example.logic

import org.example.model.Recipe

class EasyFoodSuggestionUseCase(
    private val repository: RecipesRepository
) {
    fun getTenEasyFoodSuggestions(): List<Recipe> {
        return repository.getAllRecipes()
            .filter {
                (it.minutes)!! <= 30 &&
                        (it.ingredients?.size)!! <= 5 &&
                        (it.steps?.size)!! <= 6
            }
            .shuffled()
            .take(10)
    }
}