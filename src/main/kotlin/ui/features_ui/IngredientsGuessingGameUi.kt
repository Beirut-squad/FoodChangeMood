package org.example.ui.features_ui

import org.example.logic.use_case.IngredientGuessingGameUseCase
import org.example.model.Recipe
import org.example.utils.Colors

class IngredientsGuessingGameUi(
    private val ingredientGuessingGameUseCase: IngredientGuessingGameUseCase
) {

    private val colors = Colors()

    fun show() {
        initializeIngredientGame()
        while (isIngredientGameActive()) {
            playIngredientRound()
        }
        displayIngredientGameResults()
    }

    private fun initializeIngredientGame() {
        ingredientGuessingGameUseCase.startGame()
        println(colors.cyan("\nWelcome to the Ingredient Guessing Game!"))
        println(colors.yellow("Guess the correct ingredient for each meal."))
        println(colors.yellow("Earn 1000 points per correct guess. 15 correct answers wins!"))
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
        println("\n${colors.blue("Meal: ${recipe.name}")}")
        println(colors.yellow("Which ingredient belongs to this recipe?"))
        ingredients.forEachIndexed { index, ingredient ->
            println("${colors.green("${index + 1}.")} $ingredient")
        }
    }

    private fun handleIngredientGuess(ingredients: List<String>) {
        when (val guess = getIngredientGuessInput()) {
            null -> showInvalidInputMessage()
            else -> processIngredientGuess(ingredients, guess)
        }
    }

    private fun getIngredientGuessInput(): Int? {
        print("Enter your guess (1-3): ")
        return readlnOrNull()?.toIntOrNull()
    }

    private fun showInvalidInputMessage() {
        println(colors.red("Invalid input. Please enter a number 1-3"))
    }

    private fun processIngredientGuess(ingredients: List<String>, guess: Int) {
        when {
            guess !in 1..3 -> println(colors.red("Please enter a number between 1 and 3"))
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
        println(colors.green("Correct! Current score: ${ingredientGuessingGameUseCase.getFinalScore()}"))
    }

    private fun showWrongAnswerFeedback() {
        println(colors.red("Wrong answer! Game over!"))
        println(colors.yellow("Correct ingredient was: ${ingredientGuessingGameUseCase.getCurrentCorrectGuess()}"))
        ingredientGuessingGameUseCase.endGame()
    }

    private fun displayIngredientGameResults() {
        println("\n${colors.cyan("Game Over! Final Score: ${ingredientGuessingGameUseCase.getFinalScore()}")}")
        println(colors.yellow("Thanks for playing!"))
    }

}