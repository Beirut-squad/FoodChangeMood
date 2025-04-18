package org.example.logic.use_case

import org.example.logic.RecipesRepository
import org.example.model.Recipe

class ItalianGroupMealsUseCase(
    private val repository: RecipesRepository
) {
    fun getItalianGroupMeals(): List<Recipe> {
        return repository.getAllRecipes()
            .filter { recipe ->
                val tags = recipe.tags?.map { it.replace("'", "").lowercase().trim() } ?: emptyList()
                TAG_ITALIAN in tags && TAG_LARGE_GROUPS in tags
            }
    }
    companion object {
        private const val TAG_ITALIAN = "italian"
        private const val TAG_LARGE_GROUPS = "for-large-groups"
    }
}
