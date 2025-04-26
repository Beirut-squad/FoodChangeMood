package ui.features_ui

import io.mockk.*
import org.example.logic.use_case.SweetWithNoEggsUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.ui.features_ui.SweetWithoutEggsUi
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test

class SweetWithoutEggsUiTest {

    private lateinit var ui: SweetWithoutEggsUi
    private lateinit var useCase: SweetWithNoEggsUseCase
    private lateinit var reader: Reader
    private lateinit var viewer: Viewer

    // Common test data
    private val sampleRecipe = Recipe("sweet without eggs", "no eggs...")
    private val sampleRecipe2 = Recipe("another sweet", "another description")
    private val nullRecipe: Recipe? = null

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        reader = mockk(relaxed = true)
        viewer = mockk(relaxed = true)
        ui = SweetWithoutEggsUi(useCase, viewer, reader)
    }

    private fun mockUserInputs(vararg inputs: Any?) {
        val iterator = inputs.toList().iterator()
        every { reader.readInput() } answers { iterator.next()?.toString()?.trim() }
    }

    private fun output(suggested: Recipe?) {
        viewer.printCorrectOutput("Suggested Sweet: ${suggested?.name}")
        viewer.printCorrectOutput("Description: ${suggested?.description}")
        viewer.printInfoLine("If you like this sweet, enter 1.")
        viewer.printInfoLine("If you want to see another sweet, enter anything else:")
        viewer.printInfoLine("If you want to go out press 0. ")
    }

    @Test
    fun `show() should call findSweetsFreeEggs once`() {
        //Given
        every { reader.readInput() } returns "0"

        //When
        ui.show()

        //Then
        verify(exactly = 1) {
            useCase.findSweetsFreeEggs()
        }
    }

    @Test
    fun `should print recipe when user selects 1`() {
        //Given
        mockUserInputs("1", "0")
        every { useCase.findSweetsFreeEggs() } returns sampleRecipe

        //When
        ui.show()

        //Then
        verify {
            viewer.printPlainText(RecipeFormatter.format(sampleRecipe))
        }
    }

    @Test
    fun `should ignore selection when suggestion is null`() {
        mockUserInputs("1", "0")
        every { useCase.findSweetsFreeEggs() } returns nullRecipe

        ui.show()

        verify(exactly = 0) { viewer.printPlainText(any()) }
    }

    @Test
    fun `should exit when user selects 0`() {
        //Given
        mockUserInputs("0")
        every { useCase.findSweetsFreeEggs() } returns sampleRecipe

        //When
        ui.show()

        //Then
        verify {
            output(sampleRecipe)
        }
    }

    @Test
    fun `should show new suggestion when user enters anything else 0 Or 1`() {
        //Given
        mockUserInputs("pl pl pl", "0")
        every { useCase.findSweetsFreeEggs() } returnsMany listOf(sampleRecipe, sampleRecipe2)

        //When
        ui.show()

        //Then
        verify {
            output(sampleRecipe)
            output(sampleRecipe2)
        }
    }

    @Test
    fun `should handle null suggestion gracefully`() {
        mockUserInputs("0")
        every { useCase.findSweetsFreeEggs() } returns nullRecipe

        ui.show()

        verify {
            output(nullRecipe)
        }
    }

    @Test
    fun `should handle null input by showing suggestion again`() {
        // Given
        mockUserInputs(null, "0")
        every { useCase.findSweetsFreeEggs() } returns sampleRecipe

        // When
        ui.show()

        // Then
        verify(exactly = 2) {
            output(sampleRecipe)
        }
    }

    @Test
    fun `should show new suggestion for numeric input not 0 or 1`() {
        // Given
        mockUserInputs("2", "0")
        every { useCase.findSweetsFreeEggs() } returnsMany listOf(sampleRecipe, sampleRecipe2)

        // When
        ui.show()

        // Then
        verify {
            output(sampleRecipe)
            output(sampleRecipe2)
        }
    }
}