package org.example.ui

import Colors
import org.example.logic.GameFeedback
import org.example.logic.RecipeTimeGuessGame

class RecipesTimeGuessGameUi(
    private val recipeTimeGuessGame: RecipeTimeGuessGame
) {
    private val colors = Colors()

    fun show() {
        val recipe = recipeTimeGuessGame.startNewGame()
        println(colors.blue("Guess the preparation time for: ${recipe.name}"))
        var attemptsLeft = 3
        while (attemptsLeft > 0) {
            print("Enter your guess number of minutes: ")
            val userGuessMinutes = readlnOrNull()?.toIntOrNull()
            val (newAttemptsLeft, shouldEndGame)  = processGuessInput(userGuessMinutes,attemptsLeft)
            attemptsLeft = newAttemptsLeft
            if (shouldEndGame) break
        }
    }

    private fun processGuessInput(userGuessMinutes: Int?, attemptsLeft: Int): Pair<Int, Boolean> {
        return if (userGuessMinutes == null) {
            println(colors.red("Invalid input. Please enter a valid number of minutes."))
            Pair(attemptsLeft, false)
        } else {
            val result = recipeTimeGuessGame.makeGuess(userGuessMinutes, attemptsLeft)
            handleGameFeedback(result)
            val shouldEnd =checkGameEndCondition(result)
            Pair(attemptsLeft - 1, shouldEnd)
        }
    }


    private fun checkGameEndCondition(result: GameFeedback): Boolean {
        return result is GameFeedback.CorrectGuess || result is GameFeedback.NoAttemptsLeft
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