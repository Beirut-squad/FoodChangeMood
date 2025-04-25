package org.example.logic.use_case

import org.example.logic.RecipesRepository
import org.example.model.Recipe
import org.example.model.isComplete

//Suggest a meal with more than 700 calories using a logic similar to point 6.
class ThinProblemUseCase (
    private val repository: RecipesRepository
){
    fun findThinProblem(): Recipe? {
        val allRecipes = repository.getAllRecipes()
        return allRecipes.filter{it.isComplete()}
            .filter { (it.nutrition?.calories != null && it.nutrition?.calories!! > CALORIES_IN_MEAL) }
            .shuffled().firstOrNull()
    }

    companion object{
        private const val CALORIES_IN_MEAL =700
    }
}