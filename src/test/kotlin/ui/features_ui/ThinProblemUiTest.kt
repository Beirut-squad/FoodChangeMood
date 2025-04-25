package ui.features_ui

import io.mockk.*
import org.example.logic.use_case.ThinProblemUseCase
import org.example.model.Nutrition
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.ui.features_ui.ThinProblemUi
import org.example.utils.Colors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.PrintStream
import java.time.LocalDate

class ThinProblemUiTest {

    private lateinit var thinProblemUseCase: ThinProblemUseCase
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var ui: ThinProblemUi

    @BeforeEach
    fun setUp() {
        thinProblemUseCase = mockk()
        viewer = mockk(relaxUnitFun = true)
        reader = mockk()
        ui = ThinProblemUi(thinProblemUseCase, viewer, reader)
    }

    @Test
    fun `should display suggested meal and formatted recipe when user chooses 1 then exits`() {
        // Given
        val recipe = createCompleteRecipe()
        every { thinProblemUseCase.findThinProblem() } returns recipe
        every { reader.readInput() } returnsMany listOf("1", "0")
        mockkObject(RecipeFormatter)
        every { RecipeFormatter.format(recipe) } returns "Formatted Recipe Output"
        // When
        ui.show()

        // Then
        verify(atLeast = 1) { viewer.printCorrectOutput("Suggested Meal: Salad") }
        verify(atLeast = 1) { viewer.printCorrectOutput("Description: Healthy green" , true) }
        verify(atLeast = 1) { viewer.printCorrectOutput("Calories: 850.0") }
        verify(atLeast = 1) { viewer.printCorrectOutput("Like it? Enter 1") }
        verify(atLeast = 1) { viewer.printCorrectOutput("Want another? Enter anything else:") }
        verify { viewer.printCorrectOutput("Formatted Recipe Output") }

        confirmVerified(viewer)
    }

    @Test
    fun `should handle invalid input after choosing 1 and then exit when user enters anything other than 1 or 0`() {
        // Given
        val recipe = createCompleteRecipe()
        every { thinProblemUseCase.findThinProblem() } returns recipe
        every { reader.readInput() } returnsMany listOf("1", "xyz", "1", "0")
        mockkObject(RecipeFormatter)
        every { RecipeFormatter.format(recipe) } returns "Formatted Recipe Output"

        // When
        ui.show()

        // Then
        verify(atLeast = 1) { viewer.printCorrectOutput("Suggested Meal: Salad") }
        verify(atLeast = 1) { viewer.printCorrectOutput("Description: Healthy green", true) }
        verify(atLeast = 1) { viewer.printCorrectOutput("Calories: 850.0") }
        verify(atLeast = 1) { viewer.printCorrectOutput("Like it? Enter 1") }
        verify(atLeast = 1) { viewer.printCorrectOutput("Want another? Enter anything else:") }

        verify { viewer.printCorrectOutput("Formatted Recipe Output") }

        confirmVerified(viewer)
    }


    @Test
    fun `should handle null or empty data gracefully`() {
        // Given
        every { thinProblemUseCase.findThinProblem() } returns null
        every { reader.readInput() } returns "0"

        // When
        ui.show()

        // Then
        verify { viewer.printError("No meal suggestion available.") }

        confirmVerified(viewer)
    }

    // Helper method
    private fun createCompleteRecipe(): Recipe {
        return Recipe(
            name = "Salad",
            id = "1",
            minutes = 30,
            contributorId = "100",
            submittedDate = LocalDate.now(),
            tags = listOf("Dinner"),
            nutrition = Nutrition(850.0f, 5.0f, 2.0f, 10.0f, 4.0f, 1.0f, 20.0f),
            numberOfSteps = 2,
            steps = listOf("Step 1", "Step 2"),
            description = "Healthy green",
            ingredients = listOf("Cheese", "Bread"),
            numberOfIngredients = 2
        )
    }

    private fun createIncompleteRecipe(): Recipe {
        return Recipe(
            name = "",
            id = "invalid-id",
            minutes = -5,
            contributorId = "invalid-contributor",
            submittedDate = LocalDate.now(),
            tags = listOf(""),
            nutrition = Nutrition(
                calories = 850.0f,
                totalFat = -5.0f,
                sugar = -1.0f,
                sodium = -10.0f,
                protein = -3.0f,
                saturatedFat = -0.5f,
                carbohydrates = -15.0f
            ),
            numberOfSteps = -1,
            steps = listOf(""),
            description = "",
            ingredients = listOf(""),
            numberOfIngredients = -1
        )
    }
}
