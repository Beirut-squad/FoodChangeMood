package org.example.ui.features_ui

import RecipeTimeGameResult
import org.example.logic.use_case.RecipeTimeGuessGameUseCase
import org.example.utils.Colors

class RecipesTimeGuessGameUi(
    private val recipeTimeGuessGameUseCase: RecipeTimeGuessGameUseCase,
    private val colors: Colors
) {
    fun show() {
        val recipe = recipeTimeGuessGameUseCase.startNewGame()
        println(colors.blue("Guess the preparation time for: ${recipe.name}"))
        var attemptsLeft = 3
        while (attemptsLeft > 0) {
            print(colors.yellow("Enter your guess number of minutes: "))
            val userGuessMinutes = readlnOrNull()?.toIntOrNull()
            val (newAttemptsLeft, shouldEndGame)  = evaluateUserGuessAndCheckGameStatus(userGuessMinutes,attemptsLeft)
            attemptsLeft = newAttemptsLeft
            if (shouldEndGame) break
        }
    }

    private fun evaluateUserGuessAndCheckGameStatus(userGuessMinutes: Int?, attemptsLeft: Int): Pair<Int, Boolean> {
        return if (userGuessMinutes == null) {
            println(colors.red("Invalid input. Please enter a valid number of minutes."))
            Pair(attemptsLeft, false)
        } else {
            val guessOutcome  = recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft)
            println(guessOutcome.messageResult)
            val shouldEnd =checkGameEndCondition(guessOutcome )
            Pair(attemptsLeft - 1, shouldEnd)
        }
    }


    private fun checkGameEndCondition(result: RecipeTimeGameResult): Boolean {
        return result is RecipeTimeGameResult.CorrectGuess || result is RecipeTimeGameResult.NoAttemptsLeft
    }




}