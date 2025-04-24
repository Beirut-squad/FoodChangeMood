package org.example.logic.use_case

import RecipeTimeGameResult
import org.example.logic.RecipesRepository
import org.example.model.Recipe
import kotlin.math.abs

class RecipeTimeGuessGameUseCase(private val repository: RecipesRepository) {
    private lateinit var currentRecipe: Recipe

    fun startNewGame(): Recipe {
        val recipes = repository.getAllRecipes()
            .filter (::checkNoNullValue)


        if (recipes.isEmpty()) {
            throw IllegalStateException("No recipes available with a valid preparation time.")
        }
        currentRecipe = recipes.random()
        return currentRecipe
    }

    private fun checkNoNullValue(recipe: Recipe): Boolean{
        return recipe.name != null && recipe.minutes != null
    }

    fun makeGuess(userGuessMinutes: Int, attemptsLeft: Int): RecipeTimeGameResult {
        val actualMinutes = currentRecipe.minutes!!
        val remainingAttempts = attemptsLeft - 1
        return evaluateGuess(userGuessMinutes,actualMinutes,remainingAttempts)
    }


    private fun evaluateGuess(userGuessMinutes: Int, actualMinutes: Int, remainingAttempts: Int): RecipeTimeGameResult {
        return when {
            userGuessMinutes == actualMinutes ->  RecipeTimeGameResult.CorrectGuess(actualMinutes)
            else -> evaluateWrongGuess(userGuessMinutes, actualMinutes, remainingAttempts)
        }
    }

    private fun evaluateWrongGuess(guess: Int, actual: Int, remainingAttempts: Int): RecipeTimeGameResult {
        val timeDifference = abs(guess - actual)
        return when {
            remainingAttempts == 0 -> RecipeTimeGameResult.NoAttemptsLeft(actual)
            timeDifference <= VERY_CLOSE_DIFFERENCE_THRESHOLD -> RecipeTimeGameResult.GuessIsVeryClose(remainingAttempts)
            timeDifference >= WAY_OFF_DIFFERENCE_THRESHOLD -> RecipeTimeGameResult.GuessIsWayOff(remainingAttempts)
            else -> RecipeTimeGameResult.GuessIsNotQuiteRight(remainingAttempts)
        }
    }


    companion object {
        private const val VERY_CLOSE_DIFFERENCE_THRESHOLD = 15
        private const val WAY_OFF_DIFFERENCE_THRESHOLD = 30
    }

}