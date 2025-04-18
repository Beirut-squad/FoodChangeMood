package org.example.logic.use_case


import org.example.logic.RecipesRepository
import org.example.model.Recipe
import org.example.model.isComplete


class RandomTenRecipesIncludePotatoUseCase
    (private val recipesRepo: RecipesRepository) {
    fun findPotatoMeals(): List<Recipe> {
        val allRecipes = recipesRepo.getAllRecipes()
        return allRecipes
            .filter { it.isComplete() }
            .filter { it.ingredients!!.any { ingredient ->
                ingredient.contains("potato", ignoreCase = true)
            } }.shuffled().take(NUMBER_OF_RECIPES)
    }
    companion object {
        private const val NUMBER_OF_RECIPES = 10
    }
}


