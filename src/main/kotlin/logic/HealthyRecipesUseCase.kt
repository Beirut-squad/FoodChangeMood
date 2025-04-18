package org.example.logic

import org.example.model.Recipe

class HealthyRecipesUseCase(
    private val recipesRepository: RecipesRepository,
) {
    fun getHealthyRecipes(count: Int): List<Recipe> {
        return recipesRepository.getAllRecipes()
            .filter { isQuickRecipe(it) && hasRequiredNutritionValues(it) }
            .sortedWith(
                compareBy(
                    { it.nutrition?.saturatedFat },
                    { it.nutrition?.totalFat },
                    { it.nutrition?.carbohydrates },
                )
            ).take(count)
    }

    private fun isQuickRecipe(recipe: Recipe): Boolean {
        return recipe.name != null &&
                recipe.minutes != null &&
                recipe.minutes <= MAX_PREPARATION_TIME_MINUTES
    }

    private fun hasRequiredNutritionValues(recipe: Recipe): Boolean {
        return recipe.nutrition?.totalFat != null &&
                recipe.nutrition.saturatedFat != null &&
                recipe.nutrition.carbohydrates != null
    }

    companion object {
        private const val MAX_PREPARATION_TIME_MINUTES = 15
    }
}
