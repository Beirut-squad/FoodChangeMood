package ui.features_ui

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.use_case.KetoDietUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.Viewer
import org.example.ui.features_ui.KetoDietUi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KetoDietUiTest{
    private lateinit var ketoDietUseCase: KetoDietUseCase
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var ketoDietUi: KetoDietUi
    @BeforeEach
    fun setup(){
        ketoDietUseCase = mockk(relaxed = true)
        viewer = mockk(relaxed = true)
        reader = mockk(relaxed = true)
        ketoDietUi = KetoDietUi(ketoDietUseCase,viewer,reader)
    }

    @Test
    fun `should show a welcome message to the user`(){
        // When
        ketoDietUi.show()

        // Then
        verify { viewer.printTitle("Welcome to Keto Meal Suggester ") }
    }

    @Test
    fun `should ask user to enter the number of recipes`(){
        // When
        ketoDietUi.show()

        // Then
        verify { viewer.printInfoLine("1. Suggest a Keto Recipe \n2. Go Back ") }
    }

    @Test
    fun `should show recipe name and asks user if he or she wants to proceed with recipe details when the user enters valid input`(){
        // Given
        every { reader.readInput() } returns "1"
        val fakeRecipe = mockk<Recipe>(relaxed = true)
        every { ketoDietUseCase.suggestKetoRecipe() } returns fakeRecipe

        // When
        ketoDietUi.show()

        // Then
        verify { viewer.printCorrectOutput("Meal name: ${fakeRecipe.name}") }
        verify { viewer.printInfoLine("Do you want to proceed with Recipe details ? (Y,n) ") }
    }

    @Test
    fun `should show recipe details when the user enters Y`(){
        // Given
        every { reader.readInput() } returnsMany listOf("1","Y")
        val fakeRecipe = mockk<Recipe>(relaxed = true)
        every { ketoDietUseCase.suggestKetoRecipe() } returns fakeRecipe

        // When
        ketoDietUi.show()

        // Then
        verify { viewer.printRecipeDetails(fakeRecipe) }
    }

    @Test
    fun `should ask user to enter a valid input when the user enters invalid input`(){
        // Given
        every { reader.readInput() } returns "5"

        // When
        ketoDietUi.show()

        // Then
        verify { viewer.printError("enter a valid number") }
    }

    @Test
    fun `should break when the user wants to go back`(){
        // Given
        val fakeRecipe = mockk<Recipe>(relaxed = true)
        every { reader.readInput() } returns "2"

        // When
        ketoDietUi.show()

        // Then
        verify(exactly = 0) { viewer.printCorrectOutput("Meal name: ${fakeRecipe.name}") }
        verify(exactly = 0) { viewer.printInfoLine("Do you want to proceed with Recipe details ? (Y,n) ") }
    }

    @Test
    fun `should not show recipe details when the user enters n`(){
        // Given
        every { reader.readInput() } returnsMany listOf("1","n")
        val fakeRecipe = mockk<Recipe>(relaxed = true)

        // When
        ketoDietUi.show()

        // Then
        verify(exactly = 0) { viewer.printRecipeDetails(fakeRecipe) }
    }
}