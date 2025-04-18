package org.example.logic.use_case

import org.example.logic.RecipesRepository
import org.example.model.Recipe
import kotlin.math.abs

class RecipeTimeGuessGameUseCase(private val repository: RecipesRepository) {
    private lateinit var currentRecipe: Recipe
    fun startNewGame(): Recipe {
        val recipes = repository.getAllRecipes().filter { it.minutes != null }
        if (recipes.isEmpty()) {
            throw IllegalStateException("No recipes available with a valid preparation time.")
        }
        currentRecipe = recipes.random()
        return currentRecipe
    }

    fun makeGuess(userGuessMinutes: Int, attemptsLeft: Int): GameFeedbackUseCase {
        val actualMinutes = currentRecipe.minutes
            ?: return GameFeedbackUseCase.RecipeTimeNotAvailable("Recipe preparation time not available.")
        val remainingAttempts = attemptsLeft - 1
        return evaluateGuess(userGuessMinutes,actualMinutes,remainingAttempts)
    }


    private fun evaluateGuess(userGuessMinutes: Int, actualMinutes: Int, remainingAttempts: Int): GameFeedbackUseCase {
        return when {
            userGuessMinutes == actualMinutes -> createCorrectGuessFeedback(actualMinutes)
            else -> evaluateWrongGuess(userGuessMinutes, actualMinutes, remainingAttempts)
        }
    }

    private fun createCorrectGuessFeedback(actualMinutes: Int): GameFeedbackUseCase {
        return GameFeedbackUseCase.CorrectGuess(actualMinutes)
    }

    private fun evaluateWrongGuess(guess: Int, actual: Int, remainingAttempts: Int): GameFeedbackUseCase {
        val timeDifference = abs(guess - actual)
        return when {
            remainingAttempts == 0 -> GameFeedbackUseCase.NoAttemptsLeft(actual)
            timeDifference <= VERY_CLOSE_DIFFERENCE_THRESHOLD -> GameFeedbackUseCase.GuessIsVeryClose(remainingAttempts)
            timeDifference >= WAY_OFF_DIFFERENCE_THRESHOLD -> GameFeedbackUseCase.GuessIsWayOff(remainingAttempts)
            else -> GameFeedbackUseCase.GuessIsNotQuiteRight(remainingAttempts)
        }
    }


    companion object {
        private const val VERY_CLOSE_DIFFERENCE_THRESHOLD = 15
        private const val WAY_OFF_DIFFERENCE_THRESHOLD = 30
    }

}