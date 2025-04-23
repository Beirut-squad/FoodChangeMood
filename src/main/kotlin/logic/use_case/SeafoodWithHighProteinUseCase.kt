package org.example.logic.use_case

import org.example.error.RecipeNotFoundException
import org.example.logic.RecipesRepository
import org.example.model.Recipe

class SeafoodWithHighProteinUseCase(
    private val recipesRepository: RecipesRepository
) {

    fun getSeafoodWithProteinRecipes(): List<Recipe> {
        val recipe =  recipesRepository.getAllRecipes()
            .filter {
                isSeaFoodMeal(it) &&
                it.nutrition?.protein != null
            }.sortedByDescending { it.nutrition?.protein }
        return recipe.ifEmpty{
            throw RecipeNotFoundException ("we have no seafood recipes, please come back later.")
        }
    }

    private fun isSeaFoodMeal(recipe: Recipe): Boolean {
        val tags = recipe.tags ?: throw RecipeNotFoundException("No tags in")
        return tags.any { tag ->
            tag.contains(SEAFOOD, ignoreCase = true)
        }
    }

    companion object {
        const val SEAFOOD = "seafood"
    }
}