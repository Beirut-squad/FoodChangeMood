package org.example.logic

import org.example.model.Recipe

class IraqiMealsUseCase(private val recipesRepository: RecipesRepository) {

    fun getIraqiMeals(): List<Recipe> {
        return recipesRepository.getAllRecipes().filter { recipe ->
            recipe.tags.orEmpty()
                .any { tag -> tag.contains("iraqi", ignoreCase = true) } || recipe.description.orEmpty()
                .contains("Iraq", ignoreCase = true)
        }
    }
}