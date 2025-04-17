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

    fun makeGuess(guess: Int, attemptsLeft: Int): GameFeedback {
        val actualMinutes = currentRecipe.minutes
            ?: return GameFeedback.RecipeTimeNotAvailable("Recipe preparation time not available.")

        val remainingAttempts = attemptsLeft - 1
        return if (guess == actualMinutes) {
            createCorrectGuessFeedback(actualMinutes)
        } else {
            evaluateWrongGuess(guess, actualMinutes, remainingAttempts)
        }
    }

    private fun createCorrectGuessFeedback(actualMinutes: Int): GameFeedback {
        return GameFeedback.CorrectGuess(actualMinutes)
    }

    private fun evaluateWrongGuess(guess: Int, actual: Int, remainingAttempts: Int): GameFeedback {
        if (remainingAttempts == 0) {
            return GameFeedback.NoAttemptsLeft(actual)
        }

        val timeDifference = abs(guess - actual)
        return when {
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