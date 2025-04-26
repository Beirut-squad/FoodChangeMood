package ui.features_ui

import io.mockk.*
import org.example.logic.use_case.IngredientGuessingGameUseCase
import org.example.model.Nutrition
import org.example.model.Recipe
import org.example.ui.Viewer
import org.example.ui.Reader
import org.example.ui.features_ui.IngredientsGuessingGameUi
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate
import kotlin.random.Random
import kotlin.test.Test

class IngredientsGuessingGameUiTest {
    private val ingredientGuessingGameUseCase: IngredientGuessingGameUseCase = mockk()
    private val viewer: Viewer = mockk(relaxed = true)
    private val reader: Reader = mockk(relaxed = true)
    private lateinit var ingredientsGuessingGameUi: IngredientsGuessingGameUi


    @BeforeEach
    fun setUp() {
        ingredientsGuessingGameUi = IngredientsGuessingGameUi(
            ingredientGuessingGameUseCase,
            viewer,
            reader
        )
    }

    @Test
    fun `should show game instructions when game starts`() {
        //Given
        every { ingredientGuessingGameUseCase.startGame() } just Runs
        every { ingredientGuessingGameUseCase.getFinalScore() } returns 0
        every { ingredientGuessingGameUseCase.isGameOver() } returns true
        //When
        ingredientsGuessingGameUi.show()
        //Then
        verify { viewer.printTitle("\nWelcome to the Ingredient Guessing Game!") }
        verify { viewer.printInfoLine("Guess the correct ingredient for each meal.") }
        verify { viewer.printInfoLine("Earn 1000 points per correct guess. 15 correct answers wins!") }
    }

    @Test
    fun `should display correct answer feedback when user guesses correctly`() {
        //Given
        val recipe = createRecipe()
        val ingredients = recipe.ingredients
        every { ingredientGuessingGameUseCase.startGame() } just Runs
        every { ingredientGuessingGameUseCase.getFinalScore() } returnsMany listOf(0, 1000, 1000)
        every { ingredientGuessingGameUseCase.isGameOver() } returnsMany listOf(false, true)
        every { ingredientGuessingGameUseCase.getNextRound() } returns ingredients
        every { ingredientGuessingGameUseCase.getCurrentRecipe() } returns recipe
        every { reader.readInput() } returns "1"
        every { ingredientGuessingGameUseCase.submitAnswer(any()) } returns true
        //When
        ingredientsGuessingGameUi.show()
        //Then
        verify { viewer.printCorrectOutput("Correct! Current score: 1000") }
    }

    @Test
    fun `should display error when user enters invalid number`() {
        //Given
        val recipe = createRecipe()
        val ingredients = recipe.ingredients
        every { ingredientGuessingGameUseCase.startGame() } just Runs
        every { ingredientGuessingGameUseCase.getFinalScore() } returnsMany listOf(0, 0)
        every { ingredientGuessingGameUseCase.isGameOver() } returnsMany listOf(false, true)
        every { ingredientGuessingGameUseCase.getNextRound() } returns ingredients
        every { ingredientGuessingGameUseCase.getCurrentRecipe() } returns recipe
        every { reader.readInput() } returns "5"
        //When
        ingredientsGuessingGameUi.show()
        //Then
        verify { viewer.printError("Please enter a number between 1 and 3") }
    }

    @Test
    fun `should end game and show correct answer when user guesses wrong`() {
        //Given
        val recipe = createRecipe()
        val ingredients = recipe.ingredients
        every { ingredientGuessingGameUseCase.startGame() } just Runs
        every { ingredientGuessingGameUseCase.getFinalScore() } returnsMany listOf(0, 0)
        every { ingredientGuessingGameUseCase.isGameOver() } returnsMany listOf(false, true)
        every { ingredientGuessingGameUseCase.getNextRound() } returns ingredients
        every { ingredientGuessingGameUseCase.getCurrentRecipe() } returns recipe
        every { ingredientGuessingGameUseCase.submitAnswer(any()) } returns false
        every { ingredientGuessingGameUseCase.getCurrentCorrectGuess() } returns "Ingredient1"
        every { ingredientGuessingGameUseCase.endGame() } just Runs
        every { reader.readInput() } returns "1"
        //When
        ingredientsGuessingGameUi.show()
        //Then
        verify { viewer.printError("Wrong answer! Game over!") }
        verify { viewer.printInfoLine("Correct ingredient was: Ingredient1") }
    }

    @Test
    fun `should handle null input from user`() {
        //Given
        val recipe = createRecipe()
        val ingredients = recipe.ingredients
        every { ingredientGuessingGameUseCase.startGame() } just Runs
        every { ingredientGuessingGameUseCase.getFinalScore() } returnsMany listOf(0, 0)
        every { ingredientGuessingGameUseCase.isGameOver() } returnsMany listOf(false, true)
        every { ingredientGuessingGameUseCase.getNextRound() } returns ingredients
        every { ingredientGuessingGameUseCase.getCurrentRecipe() } returns recipe
        every { reader.readInput() } returns null
        //When
        ingredientsGuessingGameUi.show()
        //When
        verify { viewer.printError("Invalid input. Please enter a number 1-3") }
    }

    @Test
    fun `should show game over message with final score`() {
        //Given
        every { ingredientGuessingGameUseCase.startGame() } just Runs
        every { ingredientGuessingGameUseCase.getFinalScore() } returns 15000
        every { ingredientGuessingGameUseCase.isGameOver() } returns true
        //When
        ingredientsGuessingGameUi.show()
        //Then
        verify { viewer.printTitle("\nGame Over! Final Score: 15000") }
        verify { viewer.printInfoLine("Thanks for playing!") }
    }


    @Test
    fun `should return early if getNextRound is null`() {
        //Given
        every { ingredientGuessingGameUseCase.startGame() } just Runs
        every { ingredientGuessingGameUseCase.getFinalScore() } returns 0
        every { ingredientGuessingGameUseCase.isGameOver() } returns false
        every { ingredientGuessingGameUseCase.getNextRound() } returns null
        every { ingredientGuessingGameUseCase.isGameOver() } returns true
        //When
        ingredientsGuessingGameUi.show()
        //Then
        verify(exactly = 0) { viewer.printLoader(any()) }
        verify(exactly = 0) { viewer.printInfoLine("Which ingredient belongs to this recipe?") }
        verify(exactly = 0) { viewer.printPlainText("Enter your guess (1-3): ", false) }
    }

    @Test
    fun `should handle all possible getNextRound null scenarios`() {
        //Given
        every { ingredientGuessingGameUseCase.startGame() } just Runs
        every { ingredientGuessingGameUseCase.getFinalScore() } returnsMany listOf(0, 0)
        every { ingredientGuessingGameUseCase.isGameOver() } returnsMany listOf(false, true)
        every { ingredientGuessingGameUseCase.getNextRound() } returns null
        //When
        ingredientsGuessingGameUi.show()
        //Then
        verify(exactly = 0) { viewer.printInfoLine("Which ingredient belongs to this recipe?") }
        verify(exactly = 0) { reader.readInput() }

    }

    @Test
    fun `should display error when guess is not between 1 and 3`() {
        //Given
        val recipe = createRecipe()
        val ingredients = recipe.ingredients
        every { ingredientGuessingGameUseCase.startGame() } just Runs
        every { ingredientGuessingGameUseCase.getFinalScore() } returnsMany listOf(0, 0)
        every { ingredientGuessingGameUseCase.isGameOver() } returnsMany listOf(false, true)
        every { ingredientGuessingGameUseCase.getNextRound() } returns ingredients
        every { ingredientGuessingGameUseCase.getCurrentRecipe() } returns recipe
        every { reader.readInput() } returns "0"
        //When
        ingredientsGuessingGameUi.show()
        //Then
        verify { viewer.printError("Please enter a number between 1 and 3") }
    }


    private fun createRecipe(
        name: String = "Test Recipe",
        id: String = "12345",
        minutes: Int = 30,
        contributorId: String = "user1",
        submittedDate: LocalDate = LocalDate.now(),
        tags: List<String> = listOf("tag1", "tag2"),
        nutrition: Nutrition = generateRandomNutrition(),
        numberOfSteps: Int = 3,
        steps: List<String> = listOf("Step 1", "Step 2", "Step 3"),
        description: String = "Test Description",
        ingredients: List<String> = listOf("Ingredient1", "Ingredient2", "Ingredient3"),
        numberOfIngredients: Int = 3
    ): Recipe {
        return Recipe(
            name, id, minutes, contributorId, submittedDate, tags, nutrition,
            numberOfSteps, steps, description, ingredients, numberOfIngredients
        )
    }

    private fun generateRandomNutrition(): Nutrition {
        return Nutrition(
            calories = Random.nextFloat() * 500,
            totalFat = Random.nextFloat() * 50,
            sugar = Random.nextFloat() * 30,
            sodium = Random.nextFloat() * 2000,
            protein = Random.nextFloat() * 40,
            saturatedFat = Random.nextFloat() * 20,
            carbohydrates = Random.nextFloat() * 100
        )
    }
}