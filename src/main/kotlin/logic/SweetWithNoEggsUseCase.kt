package org.example.logic

import org.example.model.Recipe

class SweetWithNoEggsUseCase (
    private val recipesRepo:RecipesRepository
) {
    fun findSweetsFreeEggs(): List<Recipe> {
        val allRecipes = recipesRepo.getAllRecipes()
        return allRecipes.filter {
            !it.name?.contains("egg", ignoreCase = true)!!  &&
                    !(it.description?.contains("egg", ignoreCase = true) ?: false) &&
                    (it.name?.contains("sweet", ignoreCase = true) ?: false ||
                            (it.description?.contains("sweet", ignoreCase = true) ?: false)
                            )

        }.shuffled().take(1)
    }
}