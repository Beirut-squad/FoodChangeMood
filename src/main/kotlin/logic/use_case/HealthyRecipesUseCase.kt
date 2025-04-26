package org.example.logic.use_case

import org.example.logic.RecipesRepository
import org.example.model.Recipe

class HealthyRecipesUseCase(
    private val recipesRepository: RecipesRepository,
) {
    fun getHealthyRecipes(count: Int): List<Recipe> {
        return recipesRepository.getAllRecipes()
            .filter { isQuickRecipe(it) && it.nutrition != null}
            .sortedBy { recipe ->
                val nutrition = recipe.nutrition!!
                val saturatedFat = nutrition.saturatedFat ?: Float.MAX_VALUE
                val totalFat = nutrition.totalFat ?: Float.MAX_VALUE
                val carbohydrates = nutrition.carbohydrates ?: Float.MAX_VALUE

                saturatedFat + totalFat + carbohydrates
            }.take(count)
    }

    private fun isQuickRecipe(recipe: Recipe): Boolean {
        return recipe.name != null &&
                recipe.minutes != null &&
                recipe.minutes <= MAX_PREPARATION_TIME_MINUTES
    }

    companion object {
        private const val MAX_PREPARATION_TIME_MINUTES = 15
    }
}
