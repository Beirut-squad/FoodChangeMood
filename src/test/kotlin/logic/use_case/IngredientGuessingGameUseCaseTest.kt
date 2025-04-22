package logic.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.RecipesRepository
import org.example.logic.use_case.IngredientGuessingGameUseCase
import org.example.model.Recipe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class IngredientGuessingGameUseCaseTest {
 private val recipesRepository: RecipesRepository = mockk(relaxed = true)
 private lateinit var ingredientGuessingGameUseCase: IngredientGuessingGameUseCase

 @BeforeEach
 fun setup() {
  val recipes = listOf(
   Recipe(
    id = null,
    name = "Pasta",
    minutes = 30,
    contributorId = null,
    submittedDate = null,
    tags = null,
    nutrition = null,
    numberOfSteps = null,
    steps = null,
    description = null,
    ingredients = listOf("pasta", "tomato sauce", "cheese"),
    numberOfIngredients = 3
   ),
   Recipe(
    id = null,
    name = "Pizza",
    minutes = 45,
    contributorId = null,
    submittedDate = null,
    tags = null,
    nutrition = null,
    numberOfSteps = null,
    steps = null,
    description = null,
    ingredients = listOf("dough", "tomato sauce", "cheese", "pepperoni"),
    numberOfIngredients = 4
   ),
   Recipe(
    id = null,
    name = "Salad",
    minutes = 15,
    contributorId = null,
    submittedDate = null,
    tags = null,
    nutrition = null,
    numberOfSteps = null,
    steps = null,
    description = null,
    ingredients = listOf("lettuce", "tomato", "cucumber", "olive oil"),
    numberOfIngredients = 4
   )
  )
  every { recipesRepository.getAllRecipes() } returns recipes
  ingredientGuessingGameUseCase = IngredientGuessingGameUseCase(recipesRepository)
 }

 @Test
 fun `should call getAllRecipes from recipesRepository when creating the use case`() {
  // When
  ingredientGuessingGameUseCase = IngredientGuessingGameUseCase(recipesRepository)

  // Then
  verify { recipesRepository.getAllRecipes() }
 }

 @Test
 fun `should reset score and level when starting game`() {
  // When
  ingredientGuessingGameUseCase.startGame()

  // Then
  assertThat(ingredientGuessingGameUseCase.getFinalScore()).isEqualTo(0)
  assertThat(ingredientGuessingGameUseCase.isGameOver()).isFalse()
 }

 @Test
 fun `should throw exception when no recipes with ingredients are available`() {
  // Given
  val emptyRecipes = listOf(
   Recipe(
    id = null,
    name = "Empty Recipe",
    minutes = null,
    contributorId = null,
    submittedDate = null,
    tags = null,
    nutrition = null,
    numberOfSteps = null,
    steps = null,
    description = null,
    ingredients = emptyList(),
    numberOfIngredients = 0
   )
  )
  every { recipesRepository.getAllRecipes() } returns emptyRecipes
  ingredientGuessingGameUseCase = IngredientGuessingGameUseCase(recipesRepository)

  // When & Then
  ingredientGuessingGameUseCase.startGame()
  assertThrows<IllegalStateException> { ingredientGuessingGameUseCase.getNextRound() }
 }

 @Test
 fun `should return correct ingredients list for the next round`() {
  // Given
  ingredientGuessingGameUseCase.startGame()

  // When
  val ingredients = ingredientGuessingGameUseCase.getNextRound()

  // Then
  assertThat(ingredients).isNotNull()
  assertThat(ingredients!!.size).isEqualTo(3)
  assertThat(ingredients).contains(ingredientGuessingGameUseCase.getCurrentCorrectGuess())
 }

 @Test
 fun `should end game when maximum level is reached`() {
  // Given
  ingredientGuessingGameUseCase.startGame()

  // When - play through all levels
  repeat(15) {
   ingredientGuessingGameUseCase.getNextRound()
  }
  val result = ingredientGuessingGameUseCase.getNextRound()

  // Then
  assertThat(result).isNull()
  assertThat(ingredientGuessingGameUseCase.isGameOver()).isTrue()
 }

 @Test
 fun `should increase score when submitting correct answer`() {
  // Given
  ingredientGuessingGameUseCase.startGame()
  ingredientGuessingGameUseCase.getNextRound()
  val correctAnswer = ingredientGuessingGameUseCase.getCurrentCorrectGuess()

  // When
  val result = ingredientGuessingGameUseCase.submitAnswer(correctAnswer)

  // Then
  assertThat(result).isTrue()
  assertThat(ingredientGuessingGameUseCase.getFinalScore()).isEqualTo(1000)
 }

 @Test
 fun `should not increase score when submitting wrong answer`() {
  // Given
  ingredientGuessingGameUseCase.startGame()
  ingredientGuessingGameUseCase.getNextRound()
  val wrongAnswer = "wrong ingredient"

  // When
  val result = ingredientGuessingGameUseCase.submitAnswer(wrongAnswer)

  // Then
  assertThat(result).isFalse()
  assertThat(ingredientGuessingGameUseCase.getFinalScore()).isEqualTo(0)
 }

 @Test
 fun `should return current recipe`() {
  // Given
  ingredientGuessingGameUseCase.startGame()
  ingredientGuessingGameUseCase.getNextRound()

  // When
  val recipe = ingredientGuessingGameUseCase.getCurrentRecipe()

  // Then
  assertThat(recipe).isNotNull()
  assertThat(recipe.ingredients).isNotEmpty()
 }

 @Test
 fun `should return current correct guess`() {
  // Given
  ingredientGuessingGameUseCase.startGame()
  ingredientGuessingGameUseCase.getNextRound()

  // When
  val correctGuess = ingredientGuessingGameUseCase.getCurrentCorrectGuess()

  // Then
  assertThat(correctGuess).isNotEmpty()
  assertThat(ingredientGuessingGameUseCase.getCurrentRecipe().ingredients).contains(correctGuess)
 }

 @Test
 fun `should end game when calling endGame`() {
  // Given
  ingredientGuessingGameUseCase.startGame()

  // When
  ingredientGuessingGameUseCase.endGame()

  // Then
  assertThat(ingredientGuessingGameUseCase.isGameOver()).isTrue()
 }

 @Test
 fun `should get maximum possible score when answering all rounds correctly`() {
  // Given
  ingredientGuessingGameUseCase.startGame()

  // When - play through all levels with correct answers
  repeat(15) {
   ingredientGuessingGameUseCase.getNextRound()
   ingredientGuessingGameUseCase.submitAnswer(ingredientGuessingGameUseCase.getCurrentCorrectGuess())
  }

  // Then
  assertThat(ingredientGuessingGameUseCase.getFinalScore()).isEqualTo(15000)
 }

 @Test
 fun `should ensure all ingredients in next round are different`() {
  // Given
  ingredientGuessingGameUseCase.startGame()

  // When
  val ingredients = ingredientGuessingGameUseCase.getNextRound()

  // Then
  assertThat(ingredients!!.size).isEqualTo(3)
  assertThat(ingredients.distinct().size).isEqualTo(3)
 }

 @Test
 fun `should confirm game is not over at the beginning`() {
  // Given
  ingredientGuessingGameUseCase.startGame()

  // When & Then
  assertThat(ingredientGuessingGameUseCase.isGameOver()).isFalse()
 }
}