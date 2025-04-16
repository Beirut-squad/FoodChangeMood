package org.example.logic

import org.example.model.Recipe

//Suggest a meal with more than 700 calories using a logic similar to point 6.
class ThinProblemUseCase (
    private val repository: RecipesRepository
){
    fun findThinProblem(): Recipe? {
        val allRecipes = repository.getAllRecipes()
        return allRecipes.filter {
            (it.nutrition?.calories!! > 700)
        }.shuffled().firstOrNull()
    }
}