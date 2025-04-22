package logic.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.error.RecipeNotFoundException
import org.example.logic.RecipesRepository
import org.example.logic.use_case.EasyFoodSuggestionUseCase
import org.example.model.Recipe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


// test coverage:
// 100%(1/1) class coverage -  100%(3/3) method coverage
// 100%(12/12) line coverage  -  100%(14/14) branch coverage

class EasyFoodSuggestionUseCaseTest {
    private val recipesRepository: RecipesRepository = mockk(relaxed = true)
    private lateinit var easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase

    @BeforeEach
    fun setup() {
        easyFoodSuggestionUseCase = EasyFoodSuggestionUseCase(recipesRepository)
    }

    @Test
    fun `should ignore recipes that take more than 30 minutes`() {
        // given
        val recipes = listOf(
            createEasyRecipeHelper("Quick Recipe", 10, listOf("a"), listOf("Step 1")),
            createEasyRecipeHelper("Another Quick Recipe", 20, listOf("a", "b"), listOf("Step 1")),
            createEasyRecipeHelper("Long Recipe", 35, listOf("a", "b"), listOf("Step 1")),
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        // when
        val result = easyFoodSuggestionUseCase.getTenEasyFoodSuggestions()
        // then
        assertThat(result).containsExactly(
            createEasyRecipeHelper("Quick Recipe", 10, listOf("a"), listOf("Step 1")),
            createEasyRecipeHelper("Another Quick Recipe", 20, listOf("a", "b"), listOf("Step 1")),
        )
    }

    @Test
    fun `should ignore recipes with more than 5 ingredients`() {
        // given
        val recipes = listOf(
            createEasyRecipeHelper("Few Ingredients", 10, listOf("a", "b"), listOf("Step 1")),
            createEasyRecipeHelper("Many Ingredients", 10, listOf("a", "b", "c", "d", "e", "f"), listOf("Step 1")),
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        // when
        val result = easyFoodSuggestionUseCase.getTenEasyFoodSuggestions()
        // then
        assertThat(result).containsExactly(
            createEasyRecipeHelper("Few Ingredients", 10, listOf("a", "b"), listOf("Step 1")),
        )
    }

    @Test
    fun `should ignore recipes with more than 6 steps`() {
        // given
        val recipes = listOf(
            createEasyRecipeHelper("Few Steps", 10, listOf("a", "b"), listOf("1", "2")),
            createEasyRecipeHelper("Many Steps", 10, listOf("a", "b"), listOf("1", "2", "3", "4", "5", "6", "7"))
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        // when
        val result = easyFoodSuggestionUseCase.getTenEasyFoodSuggestions()

        // then
        assertThat(result).containsExactly(
            createEasyRecipeHelper("Few Steps", 10, listOf("a", "b"), listOf("1", "2")),
        )
    }

    @Test
    fun `should return recipes less than 30 minutes, less than 5 ingredients, less than 6 steps`() {
        // given
        val recipes = listOf(
            createEasyRecipeHelper("Valid Recipe 1", 10, listOf("a", "b"), listOf("1", "2")),
            createEasyRecipeHelper(
                "Valid Recipe 2", 30, listOf("a", "b", "c", "d", "e"), listOf("1", "2", "3", "4", "5", "6")
            ),
            createEasyRecipeHelper("Invalid Time", 31, listOf("a", "b"), listOf("1", "2")),
            createEasyRecipeHelper("Invalid Ingredients", 10, listOf("a", "b", "c", "d", "e", "f"), listOf("1", "2")),
            createEasyRecipeHelper("Invalid Steps", 10, listOf("a", "b"), listOf("1", "2", "3", "4", "5", "6", "7")),
            createEasyRecipeHelper(
                "Invalid Recipe", 40, listOf("a", "b", "c", "d", "e"), listOf("1", "2", "3", "4", "5", "6", "7")
            ),
        )

        every { recipesRepository.getAllRecipes() } returns recipes

        val result = easyFoodSuggestionUseCase.getTenEasyFoodSuggestions()

        assertThat(result.map { it.name }).containsExactly("Valid Recipe 1", "Valid Recipe 2")
    }

    @Test
    fun `should throw RecipeNotFoundException when no recipes with the specified criteria`() {
        val invalidRecipes = listOf(
            createEasyRecipeHelper(
                "Invalid Recipe", 40, listOf("a", "b", "c", "d", "e", "f"), listOf("1", "2", "3", "4", "5", "6", "7")
            )
        )
        every { recipesRepository.getAllRecipes() } returns invalidRecipes

        assertThrows<RecipeNotFoundException> {
            easyFoodSuggestionUseCase.getTenEasyFoodSuggestions()
        }
    }

    @Test
    fun `should throw RecipeNotFoundException when the list to be searched on is empty`() {
        val emptyList = emptyList<Recipe>()
        every { recipesRepository.getAllRecipes() } returns emptyList

        assertThrows<RecipeNotFoundException> {
            easyFoodSuggestionUseCase.getTenEasyFoodSuggestions()
        }

    }

    @Test
    fun `should ignore recipes contains null values`() {
        // given
        val recipes = listOf(
            createEasyRecipeHelper("Recipe 1", null, listOf("a", "b"), listOf("1", "2")),
            createEasyRecipeHelper("Recipe 2", 15, null, listOf("1", "2")),
            createEasyRecipeHelper("Recipe 3", 20, listOf("a", "b"), null),
            createEasyRecipeHelper("Recipe 4", 10, listOf("a", "b"), listOf("1", "2"))
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        // when
        val result = easyFoodSuggestionUseCase.getTenEasyFoodSuggestions()
        // then
        assertThat(result).containsExactly(
            createEasyRecipeHelper("Recipe 4", 10, listOf("a", "b"), listOf("1", "2"))
        )
    }

    @Test
    fun `should throw RecipeNotFoundException when all recipe values are null`() {
        // given
        val recipesWithNullValues = listOf(createEasyRecipeHelper(null, null, null, null))

        every { recipesRepository.getAllRecipes() } returns recipesWithNullValues

        // then & when
        assertThrows<RecipeNotFoundException> {
            easyFoodSuggestionUseCase.getTenEasyFoodSuggestions()
        }
    }

    @Test
    fun `should return exactly 10 element list when there are more than 10 elements in the list`() {
        val recipes = (1..20).map {
            createEasyRecipeHelper(
                name = "Recipe $it", minutes = 10, ingredients = listOf("ingredient"), steps = listOf("step")
            )
        }
        every { recipesRepository.getAllRecipes() } returns recipes

        val result = easyFoodSuggestionUseCase.getTenEasyFoodSuggestions()

        assertThat(result.size).isEqualTo(MAX_RECIPE_COUNT)
    }

    @Test
    fun `should successfully shuffle recipes list`() {
        val recipes = (1..50).map {
            createEasyRecipeHelper(
                name = "Recipe $it", minutes = 10, ingredients = listOf("ingredient"), steps = listOf("step")
            )
        }
        every { recipesRepository.getAllRecipes() } returns recipes

        val firstRecipe = easyFoodSuggestionUseCase.getTenEasyFoodSuggestions().map { it.name }
        val secondRecipe = easyFoodSuggestionUseCase.getTenEasyFoodSuggestions().map { it.name }
        assertThat(firstRecipe).isNotEqualTo(secondRecipe)
    }

    companion object {
        private const val MAX_RECIPE_COUNT = 10
    }
}