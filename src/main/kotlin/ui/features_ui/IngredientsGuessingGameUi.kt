package org.example.ui.features_ui

import org.example.logic.use_case.IngredientGuessingGameUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.Viewer
import ui.Display

class IngredientsGuessingGameUi(
    private val ingredientGuessingGameUseCase: IngredientGuessingGameUseCase,
    private val viewer: Viewer,
    private val reader: Reader
):Display {
    
    override fun show() {
        initializeIngredientGame()
        while (isIngredientGameActive()) {
            playIngredientRound()
        }
        displayIngredientGameResults()
    }

    private fun initializeIngredientGame() {
        ingredientGuessingGameUseCase.startGame()
        viewer.printTitle("\nWelcome to the Ingredient Guessing Game!")
        viewer.printInfoLine("Guess the correct ingredient for each meal.")
        viewer.printInfoLine("Earn 1000 points per correct guess. 15 correct answers wins!")
    }

    private fun isIngredientGameActive(): Boolean {
        return ingredientGuessingGameUseCase.getFinalScore() < IngredientGuessingGameUseCase.MAX_INGREDIENT_GAME_SCORE &&
                !ingredientGuessingGameUseCase.isGameOver()
    }

    private fun playIngredientRound() {
        val ingredients = ingredientGuessingGameUseCase.getNextRound() ?: return
        val currentRecipe = ingredientGuessingGameUseCase.getCurrentRecipe()

        displayMealAndIngredients(currentRecipe, ingredients)
        handleIngredientGuess(ingredients)
    }

    private fun displayMealAndIngredients(recipe: Recipe, ingredients: List<String>) {
        viewer.printLoader("\n${"Meal: ${recipe.name}"}")
        viewer.printInfoLine("Which ingredient belongs to this recipe?")
        ingredients.forEachIndexed { index, ingredient ->
            viewer.printCorrectOutput("${"${index + 1}."} $ingredient")
        }
    }

    private fun handleIngredientGuess(ingredients: List<String>) {
        when (val guess = getIngredientGuessInput()) {
            null -> showInvalidInputMessage()
            else -> processIngredientGuess(ingredients, guess)
        }
    }

    private fun getIngredientGuessInput(): Int? {
        viewer.printPlainText("Enter your guess (1-3): ",false)
        return reader.readInput()?.toIntOrNull()
    }

    private fun showInvalidInputMessage() {
        viewer.printError("Invalid input. Please enter a number 1-3")
    }

    private fun processIngredientGuess(ingredients: List<String>, guess: Int) {
        when {
            guess !in 1..3 -> viewer.printError("Please enter a number between 1 and 3")
            else -> checkIngredientAnswer(ingredients[guess - 1])
        }
    }

    private fun checkIngredientAnswer(answer: String) {
        if (ingredientGuessingGameUseCase.submitAnswer(answer)) {
            showCorrectAnswerFeedback()
        } else {
            showWrongAnswerFeedback()
        }
    }

    private fun showCorrectAnswerFeedback() {
        viewer.printCorrectOutput("Correct! Current score: ${ingredientGuessingGameUseCase.getFinalScore()}")
    }

    private fun showWrongAnswerFeedback() {
        viewer.printError("Wrong answer! Game over!")
        viewer.printInfoLine("Correct ingredient was: ${ingredientGuessingGameUseCase.getCurrentCorrectGuess()}")
        ingredientGuessingGameUseCase.endGame()
    }

    private fun displayIngredientGameResults() {
        viewer.printTitle("\n${"Game Over! Final Score: ${ingredientGuessingGameUseCase.getFinalScore()}"}")
        viewer.printInfoLine("Thanks for playing!")
    }

}