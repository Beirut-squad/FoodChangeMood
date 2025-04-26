package org.example.logic.use_case

import org.example.error.RecipeNotFoundException
import org.example.logic.RecipesRepository
import org.example.model.Recipe

class GymHelperUseCase(
    private val recipesRepository: RecipesRepository,
) {

    fun getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(
        calories: Float,
        protein: Float
    ): List<Recipe> {
        if (calories < 0 || protein < 0) {
            throw RecipeNotFoundException("Calories or Protein can't be negative")
        }
        val result = recipesRepository.getAllRecipes()
            .filter { recipe ->
                val mealCalories = recipe.nutrition?.calories ?: Float.MIN_VALUE
                val mealProtein = recipe.nutrition?.protein ?: Float.MIN_VALUE

                isCaloriesAmountInApproximateRange(calories, mealCalories) &&
                        isProteinAmountInApproximateRange(protein, mealProtein)
            }
        return result.ifEmpty { throw RecipeNotFoundException("We have invalid data") }
    }

    private fun isCaloriesAmountInApproximateRange(
        demandedCalories: Float,
        mealCalories: Float
    ): Boolean {
        if (mealCalories == Float.MIN_VALUE) return false
        return demandedCalories in
                (mealCalories - CALORIES_APPROXIMATE_RANGE..mealCalories + CALORIES_APPROXIMATE_RANGE)
    }

    private fun isProteinAmountInApproximateRange(
        demandedProtein: Float,
        mealProtein: Float
    ): Boolean {
        if (mealProtein == Float.MIN_VALUE) return false
        return demandedProtein in
                (mealProtein - PROTEIN_APPROXIMATE_RANGE..mealProtein + PROTEIN_APPROXIMATE_RANGE)
    }

    companion object {
        private const val CALORIES_APPROXIMATE_RANGE = 10.0f
        private const val PROTEIN_APPROXIMATE_RANGE = 5.0f
    }
}