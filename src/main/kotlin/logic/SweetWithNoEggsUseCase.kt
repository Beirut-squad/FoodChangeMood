package org.example.logic

import org.example.model.Recipe

class SweetWithNoEggsUseCase (
    private val recipesRepo:RecipesRepository
) {
    fun findSweetsFreeEggs(): Recipe? {
        val allRecipes = recipesRepo.getAllRecipes()
        return allRecipes
            .filter { it.isComplete() }
            .filter { recipe ->
                !recipe.name!!.contains("egg", ignoreCase = true) &&
                        !recipe.description?.contains("egg", ignoreCase = true)!! &&
                        (recipe.name.contains("sweet", ignoreCase = true) ||
                                recipe.description.contains("sweet", ignoreCase = true))
            }.shuffled().firstOrNull()
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
}