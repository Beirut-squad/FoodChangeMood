package logic.use_case

import io.mockk.every
import io.mockk.mockk
import org.example.logic.RecipesRepository
import org.example.logic.use_case.SearchRecipeByDateUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.google.common.truth.Truth.assertThat
import org.example.error.NoRecipesFoundForTheGivenDateException
import org.example.error.RecipeNotFoundException
import utils.toDate
import java.time.format.DateTimeParseException

class SearchRecipeByDateUseCaseTest {
    private val recipesRepository: RecipesRepository = mockk(relaxed = true)
    private lateinit var searchRecipeByDateUseCase: SearchRecipeByDateUseCase

    @BeforeEach
    fun setup() {
        searchRecipeByDateUseCase = SearchRecipeByDateUseCase(recipesRepository)
    }

    @Test
    fun `should return list of recipes when matches a valid date`() {
        val recipes = listOf(
            createRecipeWithDateHelper(
                id = "111",
                name = "Test Meal 1",
                submittedDate = "2011-11-11".toDate(),
            ),
            createRecipeWithDateHelper(
                id = "222",
                name = "Test Meal 2",
                submittedDate = "2012-12-12".toDate(),
            )
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        val result = searchRecipeByDateUseCase.searchRecipeByDate("2011-11-11")
        assertThat(result).containsExactly(
            Pair("111", "Test Meal 1")
        )
    }

    @Test
    fun `should throw NoRecipesFoundForTheGivenDateException if recipe id is null`() {

        val recipes = listOf(
            createRecipeWithDateHelper(
                id = null,
                name = "Test Meal 1",
                submittedDate = "2011-11-11".toDate(),
            ),
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        assertThrows<NoRecipesFoundForTheGivenDateException> {
            searchRecipeByDateUseCase.searchRecipeByDate("2011-11-11")
        }
    }

    @Test
    fun `should throw NoRecipesFoundForTheGivenDateException if recipe name is null`() {

        val recipes = listOf(
            createRecipeWithDateHelper(
                id = "111",
                name = null,
                submittedDate = "2011-11-11".toDate(),
            ),
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        assertThrows<NoRecipesFoundForTheGivenDateException> {
            searchRecipeByDateUseCase.searchRecipeByDate("2011-11-11")
        }
    }

    @Test
    fun `should throw DateTimeParseException if entered invalid date format`() {

        val recipes = listOf(
            createRecipeWithDateHelper(
                id = "222",
                name = "Test Meal 1",
                submittedDate = "2012-12-12".toDate(),
            )
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        assertThrows<DateTimeParseException> {
            searchRecipeByDateUseCase.searchRecipeByDate("hello-12-12")
        }
    }

    @Test
    fun `should throw ParseException if entered invalid date`() {

        val recipes = listOf(
            createRecipeWithDateHelper(
                id = "222",
                name = "Test Meal 1",
                submittedDate = "2012-12-12".toDate(),
            ),
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        assertThrows<IllegalArgumentException> {
            searchRecipeByDateUseCase.searchRecipeByDate("2026-12-12")
        }
    }

    @Test
    fun `should throw NoRecipesFoundForTheGivenDateException when there is no recipes match the date entered`() {
        val recipes = listOf(
            createRecipeWithDateHelper(
                id = "111",
                name = "Test Meal 1",
                submittedDate = "2011-11-11".toDate(),
            )
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        assertThrows<NoRecipesFoundForTheGivenDateException> {
            searchRecipeByDateUseCase.searchRecipeByDate("2001-01-01")
        }
    }

    @Test
    fun `should view the details of the recipe when id is entered`() {
        val recipes = listOf(
            createRecipeWithDateHelper(
                name = "Meal 1",
                id = "111",
                steps = listOf("step 1", "step 2"),
                ingredients = listOf("ingredient 1", "ingredient 2")
            ),
            createRecipeWithDateHelper(
                name = "Meal 1",
                id = "222",
                steps = listOf("step 1", "step 2"),
                ingredients = listOf("ingredient 1", "ingredient 2")
            )
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        val result = searchRecipeByDateUseCase.viewDetailsOfRecipeByID("111")
        assertThat(result).isEqualTo(
            createRecipeWithDateHelper(
                name = "Meal 1",
                id = "111",
                steps = listOf("step 1", "step 2"),
                ingredients = listOf("ingredient 1", "ingredient 2")
            )
        )
    }

    @Test
    fun `should throw RecipeNotFoundException when no recipes matches the entered id`() {
        val recipes = listOf(
            createRecipeWithDateHelper(
                name = "Meal 1",
                id = "111",
                steps = listOf("step 1", "step 2"),
                ingredients = listOf("ingredient 1", "ingredient 2")
            ),
            createRecipeWithDateHelper(
                name = "Meal 1",
                id = "222",
                steps = listOf("step 1", "step 2"),
                ingredients = listOf("ingredient 1", "ingredient 2")
            )
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        assertThrows<RecipeNotFoundException> {
            searchRecipeByDateUseCase.viewDetailsOfRecipeByID("333")
        }
    }

    @Test
    fun `should throw RecipeNotFoundException when recipe name is null`() {
        val recipes = listOf(
            createRecipeWithDateHelper(
                name = null,
                id = "111",
                steps = listOf("step 1", "step 2"),
                ingredients = listOf("ingredient 1", "ingredient 2")
            ),
            createRecipeWithDateHelper(
                name = "Meal 1",
                id = "222",
                steps = listOf("step 1", "step 2"),
                ingredients = listOf("ingredient 1", "ingredient 2")
            )
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        val result = searchRecipeByDateUseCase.viewDetailsOfRecipeByID("222")
        assertThat(result).isEqualTo(
            createRecipeWithDateHelper(
                name = "Meal 1",
                id = "222",
                steps = listOf("step 1", "step 2"),
                ingredients = listOf("ingredient 1", "ingredient 2")
            )
        )
    }


}