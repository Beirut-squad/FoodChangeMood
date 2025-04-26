package org.example.ui.features_ui

import RecipeTimeGameResult
import org.example.logic.use_case.RecipeTimeGuessGameUseCase
import org.example.ui.Reader
import org.example.ui.Viewer
import org.example.utils.Colors
import ui.Display

class RecipesTimeGuessGameUi(
    private val recipeTimeGuessGameUseCase: RecipeTimeGuessGameUseCase,
    private val viewer: Viewer,
    private val reader: Reader
) : Display{
    override fun show() {
        val recipe = recipeTimeGuessGameUseCase.startNewGame()
        viewer.printLoader("Guess the preparation time for: ${recipe.name}")
        var attemptsLeft = 3
        while (attemptsLeft > 0) {
            viewer.printInfoLine("Enter your guess number of minutes: ")
            val userGuessMinutes = reader.readInput()?.toIntOrNull()
            val (newAttemptsLeft, shouldEndGame)  = evaluateUserGuessAndCheckGameStatus(userGuessMinutes,attemptsLeft)
            attemptsLeft = newAttemptsLeft
            if (shouldEndGame) break
        }
    }

    private fun evaluateUserGuessAndCheckGameStatus(userGuessMinutes: Int?, attemptsLeft: Int): Pair<Int, Boolean> {
        return if (userGuessMinutes == null) {
            viewer.printError("Invalid input. Please enter a valid number of minutes.")
            Pair(attemptsLeft, false)
        } else {
            val guessOutcome  = recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft)
            println(guessOutcome.messageResult)
            val shouldEnd =checkGameEndCondition(guessOutcome )
            Pair(attemptsLeft - 1, shouldEnd)
        }
    }


    fun checkGameEndCondition(result: RecipeTimeGameResult): Boolean {
        return result is RecipeTimeGameResult.CorrectGuess || result is RecipeTimeGameResult.NoAttemptsLeft
    }




}