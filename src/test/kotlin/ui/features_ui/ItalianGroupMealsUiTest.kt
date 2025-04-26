package ui.features_ui

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.example.error.RecipeNotFoundException
import org.example.logic.use_case.ItalianGroupMealsUseCase
import org.example.ui.Viewer
import org.example.ui.features_ui.ItalianGroupMealsUi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ItalianGroupMealsUiTest {

    private val italianGroupMealsUseCase: ItalianGroupMealsUseCase = mockk(relaxed = true)
    private val viewer: Viewer = mockk(relaxed = true)
    private lateinit var italianGroupMealsUi: ItalianGroupMealsUi


    @BeforeEach
    fun setup() {
        italianGroupMealsUi = ItalianGroupMealsUi(italianGroupMealsUseCase, viewer)
    }

    @Test
    fun `should print loading message before fetching data(italian meals)`() {
        italianGroupMealsUi.show()
        verify(exactly = 1) { viewer.printLoader("Loading...") }
    }

    @Test
    fun `should call getItalianGroupMeals exactly one time when show function is called`() {
        italianGroupMealsUi.show()
        verify(exactly = 1) { italianGroupMealsUseCase.getItalianGroupMeals() }
    }

    @Test
    fun `should print error message when no italian meals for groups found`() {
        every { italianGroupMealsUseCase.getItalianGroupMeals() }
            .throws(RecipeNotFoundException("No italian group meals"))

        italianGroupMealsUi.show()
        verify { viewer.printError("No italian group meals") }
    }

    @Test
    fun `print italian meal names when meals found`() {
        every { italianGroupMealsUseCase.getItalianGroupMeals() } returns listOf(
            createItalianRecipeHelper("Italian Meal 1", listOf("italian", "for-large-groups")),
            createItalianRecipeHelper("Italian Meal 2", listOf("italian", "healthy", "for-large-groups")),
        )
        italianGroupMealsUi.show()
        verifyOrder {
            viewer.printCorrectOutput("1. Italian Meal 1 ")
            viewer.printCorrectOutput("2. Italian Meal 2 ")
        }
    }

}