package org.example.logic

import org.example.model.Recipe

class IngredientGuessingGameUseCase(
    private val recipesRepository: RecipesRepository
) {

    private val allRecipes: List<Recipe> = recipesRepository.getAllRecipes()
    private var score: Int = 0
    private var currentLevel: Int = 0
    private lateinit var currentRecipe: Recipe
    private lateinit var currentCorrectGuess: String
    private var currentIngredients: List<String> = emptyList()
    private var isGameLost: Boolean = false

    fun startGame() {
        score = 0
        currentLevel = 0
        isGameLost = false
    }

    fun submitAnswer(answer: String): Boolean {
        if (answer == currentCorrectGuess) {
            score += SCORE_INCREMENT
            return true
        }
        return false
    }

    fun getNextRound(): List<String>? {
        if (currentLevel >= MAX_LEVEL) {
            isGameLost = true
            return null
        }
        currentLevel++
        setupGame()
        return currentIngredients
    }

    private fun setupGame() {
        currentRecipe = getRandomGameRecipe()
        val correctIngredient = getSingleRandomRecipeIngredient()
            ?: throw IllegalStateException("Recipe has no ingredients.")
        val wrong1 = getRandomIngredient(currentRecipe)
        val wrong2 = getRandomIngredient(currentRecipe)
        currentIngredients = listOf(correctIngredient, wrong1, wrong2).shuffled()
        currentCorrectGuess = correctIngredient
    }

    private fun getRandomIngredient(excludeRecipe: Recipe): String {
        val excludeIngredients = excludeRecipe.ingredients ?: emptyList()
        val allIngredients = getAllIngredients()
        val possibleIngredients = allIngredients.filter { it !in excludeIngredients }
        if (possibleIngredients.isEmpty()) {
            throw IllegalStateException("No available ingredients to choose as wrong options.")
        }
        return possibleIngredients.shuffled().first()
    }

    private fun getRandomGameRecipe(): Recipe {
        allRecipes.shuffled().forEach { recipe ->
            if (!recipe.ingredients.isNullOrEmpty()) {
                return recipe
            }
        }
        throw IllegalStateException("No meals with ingredients found in database!")
    }

    private fun getAllIngredients(): List<String> {
        return allRecipes
            .map { it.ingredients ?: emptyList() }
            .flatten()
            .distinct()
    }

    private fun getSingleRandomRecipeIngredient(): String? {
        return currentRecipe.ingredients?.shuffled()?.firstOrNull()
    }

    fun getFinalScore(): Int {
        return score
    }

    fun isGameOver(): Boolean = checkLoss() || currentLevel >= MAX_LEVEL

    fun getCurrentRecipe(): Recipe = currentRecipe

    fun getCurrentCorrectGuess(): String = currentCorrectGuess

    fun endGame() { currentLevel = MAX_LEVEL }

    private fun checkLoss(): Boolean {
        return isGameLost
    }


    companion object {
        private const val SCORE_INCREMENT = 1000
        private const val MAX_LEVEL = 15
        const val MAX_INGREDIENT_GAME_SCORE = 15000
    }
}