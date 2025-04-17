package org.example.logic


import org.example.model.Recipe


class RandomTenRecipesIncludePotatoUseCase
    (private val recipesRepo: RecipesRepository) {
    fun findPotatoMeals(): List<Recipe> {
        val allRecipes = recipesRepo.getAllRecipes()
        return allRecipes
            .filter { it.isComplete() }
            .filter { it.ingredients!!.any { ingredient ->
                ingredient.contains("potato", ignoreCase = true)
            } }.shuffled().take(NUMBER_OF_RECIPES)
    }
    private fun Recipe.isComplete(): Boolean {
        return ingredients != null &&
                name != null &&
                nutrition != null &&
                description != null &&
                steps != null &&
                contributorId != null &&
                id != null &&
                minutes != null &&
                numberOfIngredients != null &&
                numberOfSteps != null &&
                submittedDate != null &&
                tags != null
    }
    companion object {
        private const val NUMBER_OF_RECIPES = 10
    }
}


