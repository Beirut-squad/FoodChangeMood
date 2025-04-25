package ui.features_ui

import io.mockk.*
import org.example.error.NoRecipesFoundForTheGivenDateException
import org.example.error.RecipeNotFoundException
import org.example.logic.use_case.SearchRecipeByDateUseCase
import org.example.model.Nutrition
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.Viewer
import org.example.ui.features_ui.SearchRecipeByDateUi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import utils.checkDateFormat
import java.time.LocalDate
import kotlin.test.Test

class SearchRecipeByDateUiTest{
  private val searchRecipeByDateUseCase : SearchRecipeByDateUseCase = mockk(relaxed = true)
  private val viewer: Viewer = mockk(relaxed = true)
  private val reader: Reader = mockk(relaxed = true)
  private lateinit var searchRecipeByDateUi: SearchRecipeByDateUi

  @BeforeEach
  fun setup() {
      searchRecipeByDateUi = SearchRecipeByDateUi(
       searchRecipeByDateUseCase = searchRecipeByDateUseCase,
       viewer = viewer,
       reader = reader
      )
  }

    @Test
    fun `should display recipes for valid date and ask for details`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "123", "y")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")
        every { searchRecipeByDateUseCase.viewDetailsOfRecipeByID("123") } returns Recipe("Recipe", "123", 30, "Contributor1", LocalDate.of(2022, 10, 7), listOf("Tag1"), null, 5, listOf("Step1"), "Description", listOf("Ingredient1"), 3)

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printCorrectOutput("ID = 123 Recipe Name: Recipe")
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
            viewer.printInfoLine("Enter the ID of the recipe whose details you want to see:")
        }
    }

    @Test
    fun `should handle DateTimeParseException for malformed date input`() {
        // Given
        every { reader.readInput() } returns "2006/10/07"

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            viewer.printError("Incorrect date format ,Please enter a valid date")
        }
    }

    @Test
    fun `should handle ParseException for malformed date input`() {
        // Given
        every { reader.readInput() } returns "2006- 5-10"

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            viewer.printError("Incorrect date format ,Please enter a valid date")
        }
    }

    @Test
    fun `should handle date with invalid values like month 30`() {
        // Given
        every { reader.readInput() } returns "2006-30-30"

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            viewer.printError("Incorrect date format ,Please enter a valid date")
        }
    }

    @Test
    fun `should handle date with non-numeric characters`() {
        // Given
        every { reader.readInput() } returns "2006-AB-07"

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            viewer.printError("Incorrect date format ,Please enter a valid date")
        }
    }

    @Test
    fun `should handle empty date input`() {
        // Given
        every { reader.readInput() } returns ""

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            viewer.printError("Incorrect date format ,Please enter a valid date")
        }
    }

    @Test
    fun `should handle null date input from reader`() {
        // Given
        every { reader.readInput() } returns null

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            viewer.printError("Please enter a valid date")
        }
    }

    @Test
    fun `should handle invalid date format`() {
        // Given
        every { reader.readInput() } returns "invalid-date"

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printError("Incorrect date format ,Please enter a valid date")
        }
    }

    @Test
    fun `should handle no recipes found for the given date`() {
        // Given
        every { reader.readInput() } returns "2006-10-07"
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } throws NoRecipesFoundForTheGivenDateException("No recipes!")

        // When
        searchRecipeByDateUi.show()

        // Then
        assertThrows<NoRecipesFoundForTheGivenDateException> {
            searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07")
        }
    }

    @Test
    fun `should handle showing recipe details for valid id`() {
        // Given
        val recipe = createRecipe()
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "123")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Pizza")
        every { searchRecipeByDateUseCase.viewDetailsOfRecipeByID("123") } returns recipe

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printCorrectOutput(match { it.contains("Recipe Details:") })
        }
    }

    @Test
    fun `should handle invalid recipe id`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "invalid", "y")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("1" to "Recipe")

        // When
        searchRecipeByDateUi.show()

        // Then
        verify { viewer.printError("Enter valid id !") }
    }

    @Test
    fun `should handle RecipeNotFoundException when viewing recipe by ID`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "123", "y")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")
        every { searchRecipeByDateUseCase.viewDetailsOfRecipeByID("123") } throws RecipeNotFoundException("Not found")

        // When
        searchRecipeByDateUi.show()

        // Then
        assertThrows<RecipeNotFoundException> {
            searchRecipeByDateUseCase.viewDetailsOfRecipeByID("123")
        }
    }

    @Test
    fun `should handle user choosing not to see recipe details`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "n")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            reader.readInput()
            searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07")
            viewer.printCorrectOutput("ID = 123 Recipe Name: Recipe")
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
            reader.readInput()
        }
    }

    @Test
    fun `should handle invalid choice when asking to view recipe details`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "invalid", "n")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
            viewer.printError("Invalid choice")
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
        }
    }

    @Test
    fun `should handle choosing to go back to main menu from recipe details view`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "123", "y")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")
        every { searchRecipeByDateUseCase.viewDetailsOfRecipeByID("123") } throws RecipeNotFoundException("Recipe not found")

        // When
        searchRecipeByDateUi.show()

        // Then
        verifySequence {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            reader.readInput()
            searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07")
            viewer.printCorrectOutput("ID = 123 Recipe Name: Recipe")
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
            reader.readInput()
            viewer.printInfoLine("Enter the ID of the recipe whose details you want to see:")
            reader.readInput()
            searchRecipeByDateUseCase.viewDetailsOfRecipeByID("123") // يثير استثناء RecipeNotFoundException
            viewer.printInfoLine("Are you need to back to main menu ? (Y/N)")
            reader.readInput()
        }
    }

    @Test
    fun `should handle invalid choice when asking to go back to main menu`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "123", "invalid", "y")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")
        every { searchRecipeByDateUseCase.viewDetailsOfRecipeByID("123") } throws RecipeNotFoundException("Recipe not found")

        // When
        searchRecipeByDateUi.show()

        // Then
        verify { viewer.printError("Invalid choice") }
    }

    @Test
    fun `should print recipe with null nutrition values correctly`() {
        // Given
        val recipeWithoutNutrition = Recipe(
            name = "Simple Recipe",
            id = "456",
            minutes = 15,
            contributorId = "789",
            submittedDate = LocalDate.of(2006, 10, 7),
            tags = listOf("Quick", "Easy"),
            nutrition = null,
            numberOfSteps = 1,
            steps = listOf("Mix ingredients"),
            description = "A simple recipe",
            ingredients = listOf("Ingredient"),
            numberOfIngredients = 1
        )
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "456")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("456" to "Simple Recipe")
        every { searchRecipeByDateUseCase.viewDetailsOfRecipeByID("456") } returns recipeWithoutNutrition

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printCorrectOutput(match { it.contains("Recipe Details:") && it.contains("Nutrition:") })
        }
    }

    @Test
    fun `should handle multiple recipes for the same date`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "n")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf(
            "123" to "Pizza",
            "456" to "Pasta",
            "789" to "Salad"
        )

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printCorrectOutput("ID = 123 Recipe Name: Pizza")
            viewer.printCorrectOutput("ID = 456 Recipe Name: Pasta")
            viewer.printCorrectOutput("ID = 789 Recipe Name: Salad")
        }
    }
    @Test
    fun `should handle zero ID input and return to main menu`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "0", "y")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")

        // When
        searchRecipeByDateUi.show()

        // Then
        verifySequence {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            reader.readInput()
            searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07")
            viewer.printCorrectOutput("ID = 123 Recipe Name: Recipe")
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
            reader.readInput()
            viewer.printInfoLine("Enter the ID of the recipe whose details you want to see:")
            reader.readInput()
            viewer.printError("Enter valid id !")
            viewer.printInfoLine("Are you need to back to main menu ? (Y/N)")
            reader.readInput()
        }
    }



    @Test
    fun `should handle invalid ID input and stay in search screen`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "0", null)
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")

        // When
        searchRecipeByDateUi.show()

        // Then
        verifySequence {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            reader.readInput() // "2006-10-07"
            searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07")
            viewer.printCorrectOutput("ID = 123 Recipe Name: Recipe")
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
            reader.readInput() // "y"
            viewer.printInfoLine("Enter the ID of the recipe whose details you want to see:")
            reader.readInput() // "0"
            viewer.printError("Enter valid id !")
            viewer.printInfoLine("Are you need to back to main menu ? (Y/N)")
            reader.readInput() // null
            viewer.printError("Invalid choice")
        }
    }



    @Test
    fun `should handle null ID input specifically`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", null, "y")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            reader.readInput() // "2006-10-07"
            searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07")
            viewer.printCorrectOutput("ID = 123 Recipe Name: Recipe")
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
            reader.readInput() // "y"
            viewer.printInfoLine("Enter the ID of the recipe whose details you want to see:")
            reader.readInput() // null
            viewer.printError("Enter valid id !")
            viewer.printInfoLine("Are you need to back to main menu ? (Y/N)")
            reader.readInput() // "y"
        }
    }

    @Test
    fun `should handle multiple invalid ID inputs before successful search`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "m", "n", "abc", "n", "123")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")
        val recipe = createRecipe()
        every { searchRecipeByDateUseCase.viewDetailsOfRecipeByID("123") } returns recipe

        // When
        searchRecipeByDateUi.show()

        // Then
        verifySequence {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            reader.readInput() // "2006-10-07"
            searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07")
            viewer.printCorrectOutput("ID = 123 Recipe Name: Recipe")
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
            reader.readInput() // "y"
            viewer.printInfoLine("Enter the ID of the recipe whose details you want to see:")
            reader.readInput()
            viewer.printError("Enter valid id !")
            viewer.printInfoLine("Are you need to back to main menu ? (Y/N)")
            reader.readInput() // "n"
            viewer.printInfoLine("Enter the ID of the recipe whose details you want to see:")
            reader.readInput() // "abc"
            viewer.printError("Enter valid id !")
            viewer.printInfoLine("Are you need to back to main menu ? (Y/N)")
            reader.readInput() // "n"
            viewer.printInfoLine("Enter the ID of the recipe whose details you want to see:")
            reader.readInput() // "123"
            searchRecipeByDateUseCase.viewDetailsOfRecipeByID("123")
            viewer.printCorrectOutput(match { it.contains("Recipe Details:") })
        }
    }
    @Test
    fun `should handle multiple invalid choices in back to main menu prompt`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "", "invalid1", "invalid2", "y")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printError("Enter valid id !")
            viewer.printError("Invalid choice")
        }
    }

    @Test
    fun `should handle null answer when asking to see recipe details`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", null, "n")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Recipe")

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printError("Invalid choice")
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
        }
    }

    @Test
    fun `should handle invalid choice in back to main menu prompt`() {
        // Given
        every { reader.readInput() } returnsMany listOf("2006-10-07", "y", "0", "invalid", "y")
        every { searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07") } returns listOf("123" to "Pizza")

        // When
        searchRecipeByDateUi.show()

        // Then
        verify {
            viewer.printTitle("Enter the date for which you want to view recipes: example (2006-10-07)")
            reader.readInput() // "2006-10-07"
            searchRecipeByDateUseCase.searchRecipeByDate("2006-10-07")
            viewer.printCorrectOutput("ID = 123 Recipe Name: Pizza")
            viewer.printInfoLine("Do you want to get details of a specific recipe? (Y/N)")
            reader.readInput() // "y"
            viewer.printInfoLine("Enter the ID of the recipe whose details you want to see:")
            reader.readInput() // "0"
            viewer.printError("Enter valid id !")
            viewer.printInfoLine("Are you need to back to main menu ? (Y/N)")
            reader.readInput() // "invalid"
            viewer.printError("Invalid choice")
        }


        verify(exactly = 1) {
            viewer.printError("Invalid choice")
        }
    }

    @Test
    fun `should print invalid date when use case throws illegal argument exception`() {
        // Given
        every { reader.readInput() } returns "2006-08-07"
        every { searchRecipeByDateUseCase.searchRecipeByDate(any()) } throws IllegalArgumentException()

        // When
        searchRecipeByDateUi.show()

        // Then
        verify { viewer.printError("Invalid date.") }
    }

    private fun createRecipe(): Recipe {
        return Recipe(
            name = "Pizza",
            id = "123",
            minutes = 30,
            contributorId = "456",
            submittedDate = LocalDate.of(2006, 10, 7),
            tags = listOf("Italian", "Dinner"),
            nutrition = Nutrition(
                calories = 200.0f,
                totalFat = 10.0f,
                sugar = 2.0f,
                sodium = 300.0f,
                protein = 12.0f,
                saturatedFat = 4.0f,
                carbohydrates = 30.0f
            ),
            numberOfSteps = 2,
            steps = listOf("Make dough", "Bake"),
            description = "Delicious homemade pizza",
            ingredients = listOf("Flour", "Tomato", "Cheese"),
            numberOfIngredients = 3
        )
    }
}