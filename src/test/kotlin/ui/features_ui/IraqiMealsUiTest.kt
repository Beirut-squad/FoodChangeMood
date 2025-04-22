package ui.features_ui

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.use_case.IraqiMealsUseCase
import org.example.ui.Viewer
import org.example.ui.features_ui.IraqiMealsUi
import org.example.utils.Colors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IraqiMealsUiTest {
    private val iraqiMealsUseCase: IraqiMealsUseCase = mockk(relaxed = true)
    private val colors: Colors = mockk(relaxed = true)
    private val viewer: Viewer = mockk(relaxed = true)
    private lateinit var iraqiMealsUi: IraqiMealsUi

    @BeforeEach
    fun setup() {
        iraqiMealsUi = IraqiMealsUi(
            iraqiMealsUseCase = iraqiMealsUseCase,
            colors = colors,
            viewer = viewer
        )
    }

    @Test
    fun `should call getIraqiMeals function from iraqiMealsUseCase when show function is called`() {
        // When
        iraqiMealsUi.show()

        // Then
        verify { iraqiMealsUseCase.getIraqiMeals() }
    }

    @Test
    fun `should print the game description when show function is called`() {
        // Given
        every { iraqiMealsUseCase.getIraqiMeals() } returns emptyList()

        // When
        iraqiMealsUi.show()

        // Then
        verify(exactly = 1) { viewer.printOutputWithNewLine(colors.cyan(
            """
            ==================================
            |      Traditional Iraqi Meals    |
            ==================================
           """.trimIndent()
        )) }
    }

    @Test
    fun `should print title and all iraqi meals when show function is called`() {
        // Given
        every { iraqiMealsUseCase.getIraqiMeals() } returns listOf(
            getRecipeForIraqiMealTestsHelper(),
            getRecipeForIraqiMealTestsHelper(),
            getRecipeForIraqiMealTestsHelper()
        )

        // When
        iraqiMealsUi.show()

        // Then
        verify(exactly = 4) { viewer.printOutputWithNewLine(any()) }
    }

    @Test
    fun `should print all iraqi meals index, names, minutes, and ingredients when show function is called`() {
        // Given
        every { iraqiMealsUseCase.getIraqiMeals() } returns listOf(
            getRecipeForIraqiMealTestsHelper("1", name = "Chicken", minutes = 5, listOf("a", "b")),
            getRecipeForIraqiMealTestsHelper("2", name = "Meat", minutes = 50, listOf("c"))
        )

        // When
        iraqiMealsUi.show()

        // Then
        verify { viewer.printOutputWithNewLine(colors.green(
            "1. Chicken - 5 min - [a, b] ingredients "
        )) }
        verify { viewer.printOutputWithNewLine(colors.green(
            "2. Meat - 50 min - [c] ingredients "
        )) }
    }
}