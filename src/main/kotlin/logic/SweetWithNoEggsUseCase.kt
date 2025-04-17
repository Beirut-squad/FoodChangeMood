package org.example.logic

import org.example.model.Recipe
import org.example.model.isComplete

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
}