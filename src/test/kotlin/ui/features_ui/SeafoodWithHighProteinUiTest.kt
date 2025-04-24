package ui.features_ui

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.example.error.RecipeNotFoundException
import org.example.logic.use_case.SeafoodWithHighProteinUseCase
import org.example.ui.Viewer
import org.example.ui.features_ui.SeafoodWithHighProteinUi
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SeafoodWithHighProteinUiTest {

    private val dummyUseCase: SeafoodWithHighProteinUseCase = mockk(relaxed = true)
    private val viewer: Viewer = mockk(relaxed = true)
    private lateinit var seafoodWithHighProteinUi: SeafoodWithHighProteinUi

    @BeforeEach
    fun setup() {
        seafoodWithHighProteinUi = SeafoodWithHighProteinUi(dummyUseCase, viewer)
    }

    @Test
    fun `should return recipes with protein information and name in correct format`() {
        // Given
        val recipes = listOf(
            createSeafoodHelper("Grilled Salmon", 30f),
            createSeafoodHelper("Shrimp Scampi", 25f)
        )
        every { dummyUseCase.getSeafoodWithProteinRecipes() } returns recipes

        // When
        seafoodWithHighProteinUi.show()

        // Then
        verifyOrder {
            viewer.printCorrectOutput(
                "1. Recipe Name: Grilled Salmon \n\tProtein Amount: \n\t30.0"
            )
            viewer.printCorrectOutput(
                "2. Recipe Name: Shrimp Scampi \n\tProtein Amount: \n\t25.0"
            )
        }
    }

    @Test
    fun `should return loading in blue color when protein is loading`() {
        // Given
        val recipe = listOf(createSeafoodHelper("Grilled Salmon", 30f))
        every {
            dummyUseCase.getSeafoodWithProteinRecipes()
        } returns recipe

        // When
        seafoodWithHighProteinUi.show()

        // Then
        verify { viewer.printLoader("Loading...") }
    }

    @Test
    fun `should print red error message when no seafood recipes are found`() {
        // Given
        every {
            dummyUseCase.getSeafoodWithProteinRecipes()
        } throws RecipeNotFoundException("we have no seafood recipes, please come back later.")

        // When
        seafoodWithHighProteinUi.show()

        // Then
        verify {
            viewer.printError("Error: we have no seafood recipes, please come back later.")
        }
    }


}
