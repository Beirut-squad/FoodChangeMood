package ui.features_ui

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.use_case.RecipeTimeGuessGameUseCase
import org.example.ui.Reader
import org.example.ui.Viewer
import org.example.ui.features_ui.RecipesTimeGuessGameUi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RecipesTimeGuessGameUiTest {
    private val recipeTimeGuessGameUseCase: RecipeTimeGuessGameUseCase = mockk(relaxed = true)
    private val viewer: Viewer = mockk(relaxed = true)
    private val reader: Reader = mockk(relaxed = true)
    private lateinit var recipeTimeGuessGameUI: RecipesTimeGuessGameUi



    @BeforeEach
    fun setup() {
        recipeTimeGuessGameUI = RecipesTimeGuessGameUi(recipeTimeGuessGameUseCase, viewer , reader)
    }

    @Test
    fun `should print title message `() {
        val recipes = createRecipeTimeGuessGameHelper("Pasta")
        every { recipeTimeGuessGameUseCase.startNewGame() } returns recipes

        every { recipeTimeGuessGameUseCase.makeGuess(any(), any()) } returns
                RecipeTimeGameResult.CorrectGuess(0)
        every { reader.readInput() } returns "30"
        recipeTimeGuessGameUI.show()

        verify{ viewer.printLoader("Guess the preparation time for: Pasta") }
    }

    @Test
    fun `should handle correct guess in first attempt`() {
        val recipe = createRecipeTimeGuessGameHelper("Pizza")
        every { recipeTimeGuessGameUseCase.startNewGame() } returns recipe
        every { reader.readInput() } returns "25"
        every { recipeTimeGuessGameUseCase.makeGuess(25, 3) } returns RecipeTimeGameResult.CorrectGuess(3)

        recipeTimeGuessGameUI.show()

        verify { viewer.printInfoLine("Enter your guess number of minutes: ") }
        verify { recipeTimeGuessGameUseCase.makeGuess(25, 3) }
        // No additional attempts should be made
        verify(exactly = 1) { reader.readInput() }
    }


    @Test
    fun `should handle invalid input and continue game`() {
        val recipe = createRecipeTimeGuessGameHelper("Lasagna")
        every { recipeTimeGuessGameUseCase.startNewGame() } returns recipe
        every { reader.readInput() } returnsMany listOf("invalid", "40", "35")
        every { recipeTimeGuessGameUseCase.makeGuess(40, 3) } returns RecipeTimeGameResult.GuessIsNotQuiteRight(2)
        every { recipeTimeGuessGameUseCase.makeGuess(35, 2) } returns RecipeTimeGameResult.CorrectGuess(35)

        recipeTimeGuessGameUI.show()

        verify { viewer.printError("Invalid input. Please enter a valid number of minutes.") }
        verify { viewer.printInfoLine("Enter your guess number of minutes: ") }
        verify { recipeTimeGuessGameUseCase.makeGuess(40, 3) }
        verify { recipeTimeGuessGameUseCase.makeGuess(35, 2) }
    }

    @Test
    fun `should handle null input from reader and continue game`() {
        val recipe = createRecipeTimeGuessGameHelper("Ravioli")
        every { recipeTimeGuessGameUseCase.startNewGame() } returns recipe
        every { reader.readInput() } returnsMany listOf(null, "30")
        every { recipeTimeGuessGameUseCase.makeGuess(30, 3) } returns RecipeTimeGameResult.CorrectGuess(30)

        recipeTimeGuessGameUI.show()

        verify { viewer.printError("Invalid input. Please enter a valid number of minutes.") }
        verify(exactly = 2) { viewer.printInfoLine("Enter your guess number of minutes: ") }
        verify { recipeTimeGuessGameUseCase.makeGuess(30, 3) }
    }

    @Test
    fun `should handle multiple invalid inputs and continue game`() {
        val recipe = createRecipeTimeGuessGameHelper("Gnocchi")
        every { recipeTimeGuessGameUseCase.startNewGame() } returns recipe
        every { reader.readInput() } returnsMany listOf("", "abc", "30")
        every { recipeTimeGuessGameUseCase.makeGuess(30, 3) } returns RecipeTimeGameResult.CorrectGuess(30)

        recipeTimeGuessGameUI.show()

        verify(exactly = 2) { viewer.printError("Invalid input. Please enter a valid number of minutes.") }
        verify(exactly = 3) { viewer.printInfoLine("Enter your guess number of minutes: ") }
        verify { recipeTimeGuessGameUseCase.makeGuess(30, 3) }
    }

    @Test
    fun `should end game when no attempts left`() {
        val recipe = createRecipeTimeGuessGameHelper("Risotto")
        every { recipeTimeGuessGameUseCase.startNewGame() } returns recipe
        every { reader.readInput() } returnsMany listOf("50", "40", "30")
        every { recipeTimeGuessGameUseCase.makeGuess(50, 3) } returns RecipeTimeGameResult.GuessIsWayOff(2)
        every { recipeTimeGuessGameUseCase.makeGuess(40, 2) } returns RecipeTimeGameResult.GuessIsNotQuiteRight(1)
        every { recipeTimeGuessGameUseCase.makeGuess(30, 1) } returns RecipeTimeGameResult.NoAttemptsLeft(25)

        recipeTimeGuessGameUI.show()

        verify(exactly = 3) { viewer.printInfoLine("Enter your guess number of minutes: ") }
        verify { recipeTimeGuessGameUseCase.makeGuess(50, 3) }
        verify { recipeTimeGuessGameUseCase.makeGuess(40, 2) }
        verify { recipeTimeGuessGameUseCase.makeGuess(30, 1) }
    }


    @Test
    fun `should run through all attempts with wrong guesses`() {
        val recipe = createRecipeTimeGuessGameHelper("Spaghetti")
        every { recipeTimeGuessGameUseCase.startNewGame() } returns recipe
        every { reader.readInput() } returnsMany listOf("10", "20", "30")

        // Make sure we decrement attempts correctly and continue the loop
        every { recipeTimeGuessGameUseCase.makeGuess(10, 3) } returns RecipeTimeGameResult.GuessIsWayOff(2)
        every { recipeTimeGuessGameUseCase.makeGuess(20, 2) } returns RecipeTimeGameResult.GuessIsVeryClose(1)
        every { recipeTimeGuessGameUseCase.makeGuess(30, 1) } returns RecipeTimeGameResult.GuessIsNotQuiteRight(0)

        recipeTimeGuessGameUI.show()

        verify(exactly = 3) { viewer.printInfoLine("Enter your guess number of minutes: ") }
        verify { recipeTimeGuessGameUseCase.makeGuess(10, 3) }
        verify { recipeTimeGuessGameUseCase.makeGuess(20, 2) }
        verify { recipeTimeGuessGameUseCase.makeGuess(30, 1) }
    }
    @Test
    fun `should handle very close guess and continue game`() {
        val recipe = createRecipeTimeGuessGameHelper("Tiramisu")
        every { recipeTimeGuessGameUseCase.startNewGame() } returns recipe
        every { reader.readInput() } returnsMany listOf("15", "25")
        every { recipeTimeGuessGameUseCase.makeGuess(15, 3) } returns RecipeTimeGameResult.GuessIsVeryClose(2)
        every { recipeTimeGuessGameUseCase.makeGuess(25, 2) } returns RecipeTimeGameResult.CorrectGuess(25)

        recipeTimeGuessGameUI.show()

        verify(exactly = 2) { viewer.printInfoLine("Enter your guess number of minutes: ") }
        verify { recipeTimeGuessGameUseCase.makeGuess(15, 3) }
        verify { recipeTimeGuessGameUseCase.makeGuess(25, 2) }
    }

    @Test
    fun `should handle way off guess and continue game`() {
        val recipe = createRecipeTimeGuessGameHelper("Carbonara")
        every { recipeTimeGuessGameUseCase.startNewGame() } returns recipe
        every { reader.readInput() } returnsMany listOf("10", "20", "30")
        every { recipeTimeGuessGameUseCase.makeGuess(10, 3) } returns RecipeTimeGameResult.GuessIsWayOff(2)
        every { recipeTimeGuessGameUseCase.makeGuess(20, 2) } returns RecipeTimeGameResult.GuessIsNotQuiteRight(1)
        every { recipeTimeGuessGameUseCase.makeGuess(30, 1) } returns RecipeTimeGameResult.CorrectGuess(30)

        recipeTimeGuessGameUI.show()

        verify(exactly = 3) { viewer.printInfoLine("Enter your guess number of minutes: ") }
        verify { recipeTimeGuessGameUseCase.makeGuess(10, 3) }
        verify { recipeTimeGuessGameUseCase.makeGuess(20, 2) }
        verify { recipeTimeGuessGameUseCase.makeGuess(30, 1) }
    }

    @Test
    fun `should check game end condition correctly`() {
        // Test for correct guess
        val correctGuess = RecipeTimeGameResult.CorrectGuess(30)
        assert(recipeTimeGuessGameUI.checkGameEndCondition(correctGuess))

        // Test for no attempts left
        val noAttemptsLeft = RecipeTimeGameResult.NoAttemptsLeft(30)
        assert(recipeTimeGuessGameUI.checkGameEndCondition(noAttemptsLeft))

        // Test for continuing game
        val guessIsVeryClose = RecipeTimeGameResult.GuessIsVeryClose(2)
        assert(!recipeTimeGuessGameUI.checkGameEndCondition(guessIsVeryClose))

        val guessIsWayOff = RecipeTimeGameResult.GuessIsWayOff(1)
        assert(!recipeTimeGuessGameUI.checkGameEndCondition(guessIsWayOff))

        val guessIsNotQuiteRight = RecipeTimeGameResult.GuessIsNotQuiteRight(1)
        assert(!recipeTimeGuessGameUI.checkGameEndCondition(guessIsNotQuiteRight))

        // Test for recipe time not available
        val recipeTimeNotAvailable = RecipeTimeGameResult.RecipeTimeNotAvailable("Error message")
        assert(!recipeTimeGuessGameUI.checkGameEndCondition(recipeTimeNotAvailable))
    }

}