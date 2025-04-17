package org.example.logic

import org.example.model.Recipe

class HealthyRecipesUseCase(
    private val recipesRepository: RecipesRepository,
) {
    fun getHealthyRecipes(count: Int): List<Recipe> {
        return recipesRepository.getAllRecipes()
            .filter { isQuickRecipe(it) && hasRequiredNutritionValues(it) }
            .sortedBy(::calculateAverageNutritionValues)
            .take(count)
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

    private fun calculateAverageNutritionValues(recipe: Recipe): Float {
        val totalFat = recipe.nutrition?.totalFat ?: ZERO
        val saturatedFat = recipe.nutrition?.saturatedFat ?: ZERO
        val carbs = recipe.nutrition?.carbohydrates ?: ZERO

        return (totalFat + saturatedFat + carbs) / NUMBER_OF_ELEMENTS
    }

    companion object {
        private const val MAX_PREPARATION_TIME_MINUTES = 15
        private const val NUMBER_OF_ELEMENTS = 3f
        private const val ZERO = 0f
    }
}
