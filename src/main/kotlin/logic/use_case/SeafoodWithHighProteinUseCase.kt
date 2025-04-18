package org.example.logic.use_case

import org.example.logic.RecipesRepository
import org.example.model.Recipe

class SeafoodWithHighProteinUseCase(
    private val recipesRepository: RecipesRepository
) {

    fun getSeafoodWithProteinRecipes(): List<Recipe> {
        return recipesRepository.getAllRecipes()
            .filter {
                isSeaFoodMeal(it) &&
                it.nutrition?.protein != null
            }.sortedByDescending { it.nutrition?.protein }
    }

    private fun isSeaFoodMeal(recipe: Recipe): Boolean {
        val tags = recipe.tags ?: return false
        return tags.any { tag ->
            tag.contains(SEAFOOD, ignoreCase = true)
        }
    }

    companion object {
        const val SEAFOOD = "seafood"
    }
}