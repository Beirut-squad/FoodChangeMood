package org.example.ui.features_ui

import org.example.logic.use_case.GameFeedbackUseCase
import org.example.logic.use_case.RecipeTimeGuessGameUseCase
import org.example.utils.Colors

class RecipesTimeGuessGameUi(
    private val recipeTimeGuessGameUseCase: RecipeTimeGuessGameUseCase
) {
    private val colors = Colors()

    fun show() {
        val recipe = recipeTimeGuessGameUseCase.startNewGame()
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
            val result = recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft)
            handleGameFeedback(result)
            val shouldEnd =checkGameEndCondition(result)
            Pair(attemptsLeft - 1, shouldEnd)
        }
    }


    private fun checkGameEndCondition(result: GameFeedbackUseCase): Boolean {
        return result is GameFeedbackUseCase.CorrectGuess || result is GameFeedbackUseCase.NoAttemptsLeft
    }

    private fun handleGameFeedback(result: GameFeedbackUseCase) {
        when (result) {
            is GameFeedbackUseCase.CorrectGuess -> {
                println(colors.green("Correct! The preparation time is ${result.actualTime} minutes.")) }
            is GameFeedbackUseCase.NoAttemptsLeft -> {
                println(colors.red("No attempts left. The correct time was ${result.actualTime} minutes.")) }
            is GameFeedbackUseCase.GuessIsVeryClose -> {
                println(colors.yellow("Very close! Try again. Attempts left: ${result.remainingAttempts}")) }
            is GameFeedbackUseCase.GuessIsWayOff -> {
                println(colors.purple("Way off! Try again. Attempts left: ${result.remainingAttempts}")) }
            is GameFeedbackUseCase.GuessIsNotQuiteRight -> {
                println(colors.cyan("Not quite. Try again. Attempts left: ${result.remainingAttempts}")) }
            is GameFeedbackUseCase.RecipeTimeNotAvailable -> {
                println(colors.red("Error: ${result.errorMessage}")) }
        }
    }


}