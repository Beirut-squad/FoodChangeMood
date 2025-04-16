package org.example.ui

import Colors
import org.example.logic.GameFeedback
import org.example.logic.RecipeTimeGuessGame

class FoodChangeMoodUi (
    private val recipeTimeGuessGame: RecipeTimeGuessGame
){
    private val colors = Colors()

    private fun guessPrepTimeGame() {
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
                if (result is GameFeedback.Correct || result is GameFeedback.GameOver) {
                    return }
                attemptsLeft--
            } }
        if (attemptsLeft == 0) {
            println(colors.red("No attempts left. The game is over.")) }
    }

    private fun handleGameFeedback(result: GameFeedback) {
        when (result) {
            is GameFeedback.Correct -> {
                println(colors.green("Correct! The preparation time is ${result.actual} minutes.")) }
            is GameFeedback.GameOver -> {
                println(colors.red("No attempts left. The correct time for ${result.name} was ${result.actual} minutes.")) }
            is GameFeedback.VeryClose -> {
                println(colors.yellow("Very close! Try again. Attempts left: ${result.attemptsLeft}")) }
            is GameFeedback.WayOff -> {
                println(colors.purple("Way off! Try again. Attempts left: ${result.attemptsLeft}")) }
            is GameFeedback.NotQuite -> {
                println(colors.cyan("Not quite. Try again. Attempts left: ${result.attemptsLeft}")) }
            is GameFeedback.Error -> {
                println(colors.red("Error: ${result.message}")) }
        }
    }
    }


