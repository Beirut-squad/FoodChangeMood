package org.example.logic

import org.example.model.Recipe

//Suggest a meal with more than 700 calories using a logic similar to point 6.
class ThinProblemUseCase (
    private val repository: RecipesRepository
){
    fun findThinProblem(): Recipe? {
        val allRecipes = repository.getAllRecipes()
        return allRecipes.filter{it.isComplete()}
            .filter { (it.nutrition?.calories!! > CALORIES_IN_MEAL) }
            .shuffled().firstOrNull()
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
    companion object{
        private const val CALORIES_IN_MEAL =700
    }
}