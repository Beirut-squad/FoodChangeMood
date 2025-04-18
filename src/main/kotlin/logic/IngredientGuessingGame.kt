package org.example.logic

import org.example.model.Recipe

class IngredientGuessingGame(
    private val recipesRepository: RecipesRepository
) {

    private val allRecipes: List<Recipe> = recipesRepository.getAllRecipes()
    private var score: Int = 0
    private var currentLevel: Int = 1
    private lateinit var currentRecipe: Recipe
    private lateinit var currentGuess: String
    private lateinit var currentCorrectGuess: String
    private var currentIngredients: List<String> = emptyList()

    fun startGame() {
        score = 0
        currentLevel = 1
        currentRecipe = getRandomGameRecipe()
    }

    fun getNextRound(): List<String>? {
        if (!isGameOver()) {
            setupGame()
            return currentIngredients
        }

        return null
    }

    private fun setupGame() {
        // TODO
    }

    private fun isGameOver(): Boolean {
        return checkLoss() || currentLevel >= MAX_LEVEL
    }

    private fun checkLoss(): Boolean {
        return currentGuess != currentCorrectGuess
    }

    private fun getRandomGameRecipe(): Recipe {
        allRecipes.shuffled().forEach { recipe ->
            if (!recipe.ingredients.isNullOrEmpty()) {
                return recipe
            }
        }

        throw Exception("No meals with ingredients found in database!")
    }

    private fun getAllIngredients(): List<String> {
        return allRecipes
            .map { it.ingredients ?: emptyList() }
            .flatten()
            .distinct()
    }

    private fun getSingleRandomRecipeIngredient(): String? {
        return getAllRecipeIngredients(currentRecipe).shuffled().firstOrNull()
    }

    private fun getAllRecipeIngredients(recipe: Recipe): List<String> {
        return recipe.ingredients ?: emptyList()
    }

    private fun updateScore() {
        score += SCORE_INCREMENT
    }

    companion object {
        private const val SCORE_INCREMENT = 1000
        private const val MAX_LEVEL = 15
    }
}