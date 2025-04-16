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
            ?: return GameFeedback.Error("Recipe preparation time not available.")

        val remainingAttempts = attemptsLeft - 1
        return if (guess == actualMinutes) {
            GameFeedback.Correct(actualMinutes)
        } else {
            val timeDifference = abs(guess - actualMinutes)
            if (remainingAttempts == 0) {
                GameFeedback.GameOver(actualMinutes, currentRecipe.name.toString())
            } else {
                when {
                    timeDifference <= 15 -> GameFeedback.VeryClose(remainingAttempts)
                    timeDifference >= 30 -> GameFeedback.WayOff(remainingAttempts)
                    else -> GameFeedback.NotQuite(remainingAttempts)
                }
            }
        }
    }
}