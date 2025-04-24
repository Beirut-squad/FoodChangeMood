package logic.use_case

import RecipeTimeGameResult
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.RecipesRepository
import org.example.logic.use_case.RecipeTimeGuessGameUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RecipeTimeGuessGameUseCaseTest {
    private val recipesRepository: RecipesRepository = mockk(relaxed = true)
    private lateinit var recipeTimeGuessGameUseCase: RecipeTimeGuessGameUseCase

    @BeforeEach
    fun setup() {

        recipeTimeGuessGameUseCase = RecipeTimeGuessGameUseCase(recipesRepository)
    }

    @Test
    fun `should give a random meal from the meal list given when calling startNewGame`() {
        // Given
        val listOfRecipes = listOf(
            makeRecipeHelper("Cool meal", 45),
            makeRecipeHelper("Not cool meal", null),
            makeRecipeHelper("Hey meal", 44),
            makeRecipeHelper("The meal", 66)
        )
        every { recipesRepository.getAllRecipes() } returns listOfRecipes

        // When
        val result = recipeTimeGuessGameUseCase.startNewGame()

        // Then
        assertThat(result).isIn(listOfRecipes)
    }

    @Test
    fun `should give a random meal that has a name when calling startNewGame`() {
        // Given
        val listOfRecipes = listOf(
            makeRecipeHelper("Cool meal", 45),
            makeRecipeHelper("Not cool meal", null),
            makeRecipeHelper("Hey meal", 44),
            makeRecipeHelper(null, 66)
        )
        every { recipesRepository.getAllRecipes() } returns listOfRecipes

        // When
        val result = recipeTimeGuessGameUseCase.startNewGame()

        // Then
        assertThat(result.name).isNotNull()
    }

    @Test
    fun `should give a random meal that has minutes when calling startNewGame`() {
        // Given
        val listOfRecipes = listOf(
            makeRecipeHelper("Cool meal", 45),
            makeRecipeHelper("Not cool meal", null),
            makeRecipeHelper("Hey meal", 44),
            makeRecipeHelper(null, 66)
        )
        every { recipesRepository.getAllRecipes() } returns listOfRecipes

        // When
        val result = recipeTimeGuessGameUseCase.startNewGame()

        // Then
        assertThat(result.minutes).isNotNull()
    }

    @Test
    fun `should call getAllRecipes from recipesRepository when calling startNewGame with valid recipes`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            makeRecipeHelper("Cool meal", 45),
            makeRecipeHelper("Not cool meal", null),
            makeRecipeHelper("Hey meal", 44),
            makeRecipeHelper(null, 66)
        )

        // When
        recipeTimeGuessGameUseCase.startNewGame()

        // Then
        verify { recipesRepository.getAllRecipes() }
    }

    @Test
    fun `should throw exception when getAllRecipes gives empty list in startNewGame`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns emptyList()

        // When && Then
        assertThrows<Exception> { recipeTimeGuessGameUseCase.startNewGame() }
    }

    @Test
    fun `should throw exception when all recipes in getAllRecipes has null names in startNewGame`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            makeRecipeHelper(null, 40),
            makeRecipeHelper(null, 60)
        )

        // When && Then
        assertThrows<Exception> { recipeTimeGuessGameUseCase.startNewGame() }
    }

    @Test
    fun `should throw exception when all recipes in getAllRecipes has null minutes in startNewGame`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            makeRecipeHelper("Cool meal", null),
            makeRecipeHelper("Not cool meal", null)
        )

        // When && Then
        assertThrows<Exception> { recipeTimeGuessGameUseCase.startNewGame() }
    }

    @Test
    fun `should throw exception when calling makeGuess with zero attempts left`() {
        // Given
        val userGuessMinutes = 5
        val attemptsLeft = 0

        // When && Then
        assertThrows<Exception> { recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft) }
    }

    @Test
    fun `should throw exception when calling makeGuess with minus attempts left`() {
        // Given
        val userGuessMinutes = 5
        val attemptsLeft = -5

        // When && Then
        assertThrows<Exception> { recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft) }
    }

    @Test
    fun `should throw exception when calling makeGuess with more than 3 attempts left`() {
        // Given
        val userGuessMinutes = 5
        val attemptsLeft = 4

        // When && Then
        assertThrows<Exception> { recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft) }
    }

    @Test
    fun `should throw exception when calling makeGuess with zero user minutes guess left`() {
        // Given
        val userGuessMinutes = 0
        val attemptsLeft = 3

        // When && Then
        assertThrows<Exception> { recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft) }
    }

    @Test
    fun `should throw exception when calling makeGuess with minus user minutes guess left`() {
        // Given
        val userGuessMinutes = -5
        val attemptsLeft = 5

        // When && Then
        assertThrows<Exception> { recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft) }
    }

    @Test
    fun `should give correct guess when user guess is the same as current recipe minutes with right actual time`() {
        // Given
        val actualTime = 50
        every { recipesRepository.getAllRecipes() } returns listOf(
            makeRecipeHelper("Cool meal", actualTime)
        )
        val userGuessMinutes = 50
        val attemptsLeft = 3

        // When
        recipeTimeGuessGameUseCase.startNewGame()
        val result = recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft)

        // Then
        assertThat(result is RecipeTimeGameResult.CorrectGuess).isTrue()
    }

    @Test
    fun `should give no attempts left when user guess is not the same as current recipe minutes and attempts left is 1`() {
        // Given
        val actualTime = 50
        every { recipesRepository.getAllRecipes() } returns listOf(
            makeRecipeHelper("Cool meal", actualTime)
        )
        val userGuessMinutes = 60
        val attemptsLeft = 1

        // When
        recipeTimeGuessGameUseCase.startNewGame()
        val result = recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft)

        // Then
        assertThat(result is RecipeTimeGameResult.NoAttemptsLeft).isTrue()
    }

    @Test
    fun `should give guess is very close when user guess is in a very close distance to current recipe minutes and attempts left is more than 1`() {
        // Given
        val actualTime = 50
        val closeTime = 15
        every { recipesRepository.getAllRecipes() } returns listOf(
            makeRecipeHelper("Cool meal", actualTime)
        )
        val userGuessMinutes = actualTime + closeTime
        val attemptsLeft = 3
        // When
        recipeTimeGuessGameUseCase.startNewGame()
        val result = recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft)

        // Then
        assertThat(result is RecipeTimeGameResult.GuessIsVeryClose).isTrue()
    }

    @Test
    fun `should give guess is way off when user guess is really far from current recipe minutes and attempts left is more than 1`() {
        // Given
        val actualTime = 50
        val offTime = 30
        every { recipesRepository.getAllRecipes() } returns listOf(
            makeRecipeHelper("Cool meal", actualTime)
        )
        val userGuessMinutes = actualTime + offTime
        val attemptsLeft = 3
        // When
        recipeTimeGuessGameUseCase.startNewGame()
        val result = recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft)

        // Then
        assertThat(result is RecipeTimeGameResult.GuessIsWayOff).isTrue()
    }

    @Test
    fun `should give guess is not quite right when user guess is in a middle way to current recipe minutes and attempts left is more than 1`() {
        // Given
        val actualTime = 50
        val notQuiteTime = 20
        every { recipesRepository.getAllRecipes() } returns listOf(
            makeRecipeHelper("Cool meal", actualTime)
        )
        val userGuessMinutes = actualTime + notQuiteTime
        val attemptsLeft = 3
        // When
        recipeTimeGuessGameUseCase.startNewGame()
        val result = recipeTimeGuessGameUseCase.makeGuess(userGuessMinutes, attemptsLeft)

        // Then
        assertThat(result is RecipeTimeGameResult.GuessIsNotQuiteRight).isTrue()
    }




}