package org.example.logic

import org.example.model.Recipe
import kotlin.collections.sortedWith

class HealthyRecipesUseCase(
    private val recipesRepository: RecipesRepository,
) {
    fun getHealthyRecipes(count: Int): List<Recipe> {
        val allRecipes = recipesRepository.getAllRecipes()
        val nutritionAverages = calculateNutritionAverages(allRecipes)

        return allRecipes
            .filter { isQuickRecipe(it) }
            .filter { hasLowFatAndCarb(it, nutritionAverages) }
            .sortedWith(
                compareBy(
                    { it.nutrition?.totalFat },
                    { it.nutrition?.saturatedFat },
                    { it.nutrition?.carbohydrates }
                ))
            .take(count)
    }

    private fun isQuickRecipe(recipe: Recipe): Boolean {
        return recipe.name != null &&
                recipe.minutes != null &&
                recipe.minutes <= MAX_PREPARATION_TIME_MINUTES
    }

    private fun hasLowFatAndCarb(recipe: Recipe, averages: NutritionAverages): Boolean {
        return recipe.nutrition?.totalFat != null &&
                recipe.nutrition?.saturatedFat != null &&
                recipe.nutrition?.carbohydrates != null &&
                recipe.nutrition.totalFat < averages.totalFat * LOW_AVERAGE_RATIO &&
                recipe.nutrition.saturatedFat < averages.saturatedFat * LOW_AVERAGE_RATIO &&
                recipe.nutrition.carbohydrates < averages.carbohydrates * LOW_AVERAGE_RATIO
    }

    private fun calculateNutritionAverages(recipes: List<Recipe>): NutritionAverages {
        return NutritionAverages(
            totalFat = recipes.mapNotNull { it.nutrition?.totalFat }.average(),
            saturatedFat = recipes.mapNotNull { it.nutrition?.saturatedFat }.average(),
            carbohydrates = recipes.mapNotNull { it.nutrition?.carbohydrates }.average()
        )
    }

    private data class NutritionAverages(
        val totalFat: Double,
        val saturatedFat: Double,
        val carbohydrates: Double
    )

    companion object {
        private const val MAX_PREPARATION_TIME_MINUTES = 15
        private const val LOW_AVERAGE_RATIO = 0.5
    }
}
