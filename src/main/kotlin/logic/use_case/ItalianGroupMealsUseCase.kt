package org.example.logic.use_case

import org.example.error.RecipeNotFoundException
import org.example.logic.RecipesRepository
import org.example.model.Recipe

class ItalianGroupMealsUseCase(
    private val repository: RecipesRepository
) {
    fun getItalianGroupMeals(): List<Recipe> {
        val result =  repository.getAllRecipes()
            .filter { recipe ->
                val tags = recipe.tags?.map { it.replace("'", "").lowercase().trim() } ?: emptyList()
                TAG_ITALIAN in tags && TAG_LARGE_GROUPS in tags
            }
        return result.ifEmpty {
            throw RecipeNotFoundException("No italian group meals")
        }
    }
    companion object {
        private const val TAG_ITALIAN = "italian"
        private const val TAG_LARGE_GROUPS = "for-large-groups"
    }
}
