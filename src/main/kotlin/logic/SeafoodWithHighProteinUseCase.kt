package org.example.logic

import org.example.model.Recipe

class SeafoodWithHighProteinUseCase(
    private val recipesRepository: RecipesRepository
) {

    fun getSeafoodWithProteinRecipes(): List<Recipe> {

        return recipesRepository.getAllRecipes()
            .filter {
                isSeaFoodMeal(it) &&
                it.nutrition?.protein != null
            }
            .sortedByDescending { it.nutrition?.protein ?: 0f }
    }

    private fun isSeaFoodMeal(recipe: Recipe): Boolean {
        val tags = recipe.tags ?: return false
        return tags.any { tag ->
            tag.contains("seafood", ignoreCase = true)
        }
    }
}