package org.example.logic

import org.example.model.Recipe

class HealthyRecipesUseCase(
    private val recipesRepository: RecipesRepository,
) {
    fun getHealthyRecipes(count: Int): List<Recipe> {
        val allRecipes = recipesRepository.getAllRecipes()

        return allRecipes
            .filter { isQuickRecipe(it) }
            .filter(::filteredNutritionValues)
            .sortedBy(::calculateAverageNutritionValues)
            .take(count)
    }

    private fun isQuickRecipe(recipe: Recipe): Boolean {
        return recipe.name != null &&
                recipe.minutes != null &&
                recipe.minutes <= MAX_PREPARATION_TIME_MINUTES
    }

    private fun filteredNutritionValues(recipe: Recipe) : Boolean{
        return recipe.nutrition?.totalFat != null &&
                recipe.nutrition.saturatedFat != null &&
                recipe.nutrition.carbohydrates != null
    }
    private fun calculateAverageNutritionValues(recipe: Recipe): Float {
        val totalFat = recipe.nutrition?.totalFat!!
        val saturatedFat = recipe.nutrition.saturatedFat!!
        val carbs = recipe.nutrition.carbohydrates!!

        return (totalFat + saturatedFat + carbs) / NUMBER_OF_ELEMENTS
    }

    companion object {
        private const val MAX_PREPARATION_TIME_MINUTES = 15
        private const val NUMBER_OF_ELEMENTS = 3f
    }
}
