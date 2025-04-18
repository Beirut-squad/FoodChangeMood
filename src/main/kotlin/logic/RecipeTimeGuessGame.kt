package org.example.logic

import org.example.model.Recipe
import kotlin.math.abs

class RecipeTimeGuessGame(private val repository: RecipesRepository) {
    private lateinit var currentRecipe: Recipe
    fun startNewGame(): Recipe {
        val recipes = repository.getAllRecipes().filter { it.minutes != null }
        if (recipes.isEmpty()) {
            throw IllegalStateException("No recipes available with a valid preparation time.")
        }
        currentRecipe = recipes.random()
        return currentRecipe
    }

    fun makeGuess(userGuessMinutes: Int, attemptsLeft: Int): GameFeedback {
        val actualMinutes = currentRecipe.minutes
            ?: return GameFeedback.RecipeTimeNotAvailable("Recipe preparation time not available.")
        val remainingAttempts = attemptsLeft - 1
        return evaluateGuess(userGuessMinutes,actualMinutes,remainingAttempts)
    }


    private fun evaluateGuess(userGuessMinutes: Int, actualMinutes: Int, remainingAttempts: Int): GameFeedback {
        return when {
            userGuessMinutes == actualMinutes -> createCorrectGuessFeedback(actualMinutes)
            else -> evaluateWrongGuess(userGuessMinutes, actualMinutes, remainingAttempts)
        }
    }

    private fun createCorrectGuessFeedback(actualMinutes: Int): GameFeedback {
        return GameFeedback.CorrectGuess(actualMinutes)
    }

    private fun evaluateWrongGuess(guess: Int, actual: Int, remainingAttempts: Int): GameFeedback {
        val timeDifference = abs(guess - actual)
        return when {
            remainingAttempts == 0 -> GameFeedback.NoAttemptsLeft(actual)
            timeDifference <= VERY_CLOSE_DIFFERENCE_THRESHOLD -> GameFeedback.GuessIsVeryClose(remainingAttempts)
            timeDifference >= WAY_OFF_DIFFERENCE_THRESHOLD -> GameFeedback.GuessIsWayOff(remainingAttempts)
            else -> GameFeedback.GuessIsNotQuiteRight(remainingAttempts)
        }
    }


    companion object {
        private const val VERY_CLOSE_DIFFERENCE_THRESHOLD = 15
        private const val WAY_OFF_DIFFERENCE_THRESHOLD = 30
    }

}