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
        val result =  recipesRepository.getAllRecipes()
            .filter { recipe ->
                isCaloriesAmountInApproximateRange(
                    demandedCalories = calories,
                    mealCalories = recipe.nutrition?.calories ?: 0f
                )
            }
            .filter { recipe ->
                isProteinAmountInApproximateRange(
                    demandedProtein = protein,
                    mealProtein = recipe.nutrition?.protein ?: 0f
                )
            }
        return result.ifEmpty { throw RecipeNotFoundException ("WE have an exception") }
    }

    private fun isCaloriesAmountInApproximateRange(
        demandedCalories: Float,
        mealCalories: Float
    ): Boolean {
        return demandedCalories in
                (mealCalories - CALORIES_APPROXIMATE_RANGE..mealCalories + CALORIES_APPROXIMATE_RANGE)
    }

    private fun isProteinAmountInApproximateRange(
        demandedProtein: Float,
        mealProtein: Float
    ): Boolean {
        return demandedProtein in
                (mealProtein - PROTEIN_APPROXIMATE_RANGE..mealProtein + PROTEIN_APPROXIMATE_RANGE)
    }

    companion object {
        private const val CALORIES_APPROXIMATE_RANGE = 10.0f
        private const val PROTEIN_APPROXIMATE_RANGE = 5.0f
    }
}