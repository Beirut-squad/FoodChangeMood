package org.example.logic.use_case

import org.example.error.RecipeNotFoundException
import org.example.logic.RecipesRepository
import org.example.model.Recipe

class EasyFoodSuggestionUseCase(
    private val repository: RecipesRepository
) {
    fun getTenEasyFoodSuggestions(): List<Recipe> {
        val easyRecipes = repository.getAllRecipes()
            .filter(::isEasyRecipe)
            .shuffled()
            .take(SUGGESTION_COUNT)

        return if (easyRecipes.isNotEmpty())
            easyRecipes
        else
            throw RecipeNotFoundException("No Recipes Found")
    }

    private fun isEasyRecipe(recipe: Recipe) : Boolean{
        return (recipe.minutes ?: Int.MAX_VALUE) <= MAX_EASY_MINUTES &&
                (recipe.ingredients?.size ?: Int.MAX_VALUE) <= MAX_INGREDIENTS &&
                (recipe.steps?.size ?: Int.MAX_VALUE) <= MAX_STEPS
    }

    companion object {
        private const val MAX_EASY_MINUTES = 30
        private const val MAX_INGREDIENTS = 5
        private const val MAX_STEPS = 6
        private const val SUGGESTION_COUNT = 10
    }

}