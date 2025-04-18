package org.example.ui

import Colors
import org.example.logic.GameFeedback
import org.example.logic.RecipeTimeGuessGame

class RecipesTimeGuessGameUi (
    private val recipeTimeGuessGame: RecipeTimeGuessGame

){
    private val colors = Colors()

     fun show() {
        val recipe = recipeTimeGuessGame.startNewGame()
        println(colors.blue("Guess the preparation time for: ${recipe.name}"))

        var attemptsLeft = 3
        while (attemptsLeft > 0) {
            print("Enter your guess number of minutes: ")
            val guess = readlnOrNull()?.toIntOrNull()
            if (guess == null) {
                println(colors.red("Invalid input. Please enter a valid number of minutes."))
            } else {
                val result = recipeTimeGuessGame.makeGuess(guess, attemptsLeft)
                handleGameFeedback(result)
                if (result is GameFeedback.CorrectGuess || result is GameFeedback.NoAttemptsLeft) {
                    return
                }
                attemptsLeft--
            }
        }

        if (attemptsLeft == 0) {
            println(colors.red("No attempts left. The game is over."))
        }
    }

    private fun handleGameFeedback(result: GameFeedback) {
        when (result) {
            is GameFeedback.CorrectGuess -> {
                println(colors.green("Correct! The preparation time is ${result.actualTime} minutes.")) }
            is GameFeedback.NoAttemptsLeft -> {
                println(colors.red("No attempts left. The correct time was ${result.actualTime} minutes.")) }
            is GameFeedback.GuessIsVeryClose -> {
                println(colors.yellow("Very close! Try again. Attempts left: ${result.remainingAttempts}")) }
            is GameFeedback.GuessIsWayOff -> {
                println(colors.purple("Way off! Try again. Attempts left: ${result.remainingAttempts}")) }
            is GameFeedback.GuessIsNotQuiteRight -> {
                println(colors.cyan("Not quite. Try again. Attempts left: ${result.remainingAttempts}")) }
            is GameFeedback.RecipeTimeNotAvailable -> {
                println(colors.red("Error: ${result.errorMessage}")) }
        }
    }
}