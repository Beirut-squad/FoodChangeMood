package org.example.logic.use_case

import org.example.error.RecipeNotFoundException
import org.example.logic.RecipesRepository
import org.example.model.Recipe

class GlobalFoodCultureUseCase(private val recipesRepository: RecipesRepository) {
    companion object {
        private const val NUMBER_OF_RECIPES = 20
    }

    fun getRandomMealsByCountry(countryName: String): List<Recipe> {
        return recipesRepository.getAllRecipes()
            .filter { recipe -> isValidNameAndDescription(recipe) && isRecipeRelatedToCountry(recipe, countryName) }
            .shuffled()
            .take(NUMBER_OF_RECIPES)
            .ifEmpty { throw RecipeNotFoundException("No Recipes Found") }
    }

    private fun isValidNameAndDescription(recipe: Recipe): Boolean {
        return !recipe.name.isNullOrBlank() &&
                !recipe.description.isNullOrBlank()
    }

    private fun isRecipeRelatedToCountry(recipe: Recipe, countryName: String): Boolean {
        return recipe.name.orEmpty().contains(countryName, ignoreCase = true)
                || recipe.description.orEmpty().contains(countryName, ignoreCase = true)
                || recipe.tags.orEmpty().any { it.contains(countryName, ignoreCase = true) }
    }
}
