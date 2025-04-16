package org.example.logic


import org.example.model.Recipe


class RandomTenRecipesIncludePotatoUseCase
    (private val recipesRepo: RecipesRepository) {
    fun findPotatoMeals(): List<Recipe> {
        val allRecipes = recipesRepo.getAllRecipes()
        return allRecipes.filter { recipe ->
            recipe.ingredients?.any { ingredient ->
                ingredient.contains("potato", ignoreCase = true)
            } == true
        }.shuffled().take(NUMBER_OF_RECIPES)
    }

    companion object {
        private const val NUMBER_OF_RECIPES = 10
    }
}


