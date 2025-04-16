package org.example.logic

import org.example.model.Recipe

class ItalianGroupMealsUseCase(
    private val repository: RecipesRepository
) {
    fun getItalianGroupMeals(): List<Recipe> {
        return repository.getAllRecipes()
            .filter { recipe ->
                val tags = recipe.tags?.map { it.replace("'", "").lowercase().trim() } ?: emptyList()
                "italian" in tags && "for-large-groups" in tags
            }
    }
}
