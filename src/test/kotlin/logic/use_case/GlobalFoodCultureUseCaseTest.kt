package logic.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.error.RecipeNotFoundException
import org.example.logic.RecipesRepository
import org.example.logic.use_case.GlobalFoodCultureUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class GlobalFoodCultureUseCaseTest {

    private val recipesRepository: RecipesRepository = mockk(relaxed = true)
    private lateinit var globalFoodCultureUseCase: GlobalFoodCultureUseCase

    @BeforeEach
    fun setup() {
        globalFoodCultureUseCase = GlobalFoodCultureUseCase(recipesRepository)
    }

    @ParameterizedTest
    @CsvSource(
        "egypt", "Egypt", "EGYPT", "EGYpt"
    )
    fun `should return recipes matching country when country name in any case`(
        countryName: String
    ) {
        val recipes = listOf(
            createRecipeHelper(name = "Egyptian Koshari", description = "This is an Egyptian Meal"),
            createRecipeHelper(name = "Italian Pizza", description = "This is an Italian Meal"),
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        val result = globalFoodCultureUseCase.getRandomMealsByCountry(countryName)
        assertThat(result).containsExactly(
            createRecipeHelper(name = "Egyptian Koshari", description = "This is an Egyptian Meal"),
        )
    }

    @Test
    fun `should return exactly 20 recipes list when there are more than 20 recipes in the list`() {
        val recipes = (1..RECIPE_COLLECTION_SIZE).map {
            createRecipeHelper(name = "Egyptian Koshari", description = "This is an Egyptian Meal")
        }
        every { recipesRepository.getAllRecipes() } returns recipes
        val result = globalFoodCultureUseCase.getRandomMealsByCountry("egypt")
        assertThat(result).hasSize(MAX_RECIPE_COUNT)
    }

    @Test
    fun `should return recipes matching country name in description`() {
        val recipes = listOf(
            createRecipeHelper(name = "Mexican Meal", description = "A famous dish from Mexico"),
            createRecipeHelper(name = "Italian", description = "Spaghetti from Italy")
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        val result = globalFoodCultureUseCase.getRandomMealsByCountry("Mexico")
        assertThat(result).containsExactly(
            createRecipeHelper(name = "Mexican Meal", description = "A famous dish from Mexico"),
        )
    }

    @Test
    fun `should return recipes matching country name in tags`() {
        val recipes = listOf(
            createRecipeHelper(
                name = "Korean Meal",
                description = "This is Korean Meal",
                tags = listOf("Korean", "Spicy")
            ),
            createRecipeHelper(
                name = "Some Meal",
                description = "This is an Indian Meal",
                tags = listOf("India", "Curry")
            )
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        val result = globalFoodCultureUseCase.getRandomMealsByCountry("India")
        assertThat(result).containsExactly(
            createRecipeHelper(
                name = "Some Meal",
                description = "This is an Indian Meal",
                tags = listOf("India", "Curry")
            )

        )
    }

    @Test
    fun `should return throw RecipeNotFoundException if no recipes match country`() {
        val recipes = listOf(
            createRecipeHelper(name = "French Toast", description = "This is a French Meal"),
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        assertThrows<RecipeNotFoundException> {
            globalFoodCultureUseCase.getRandomMealsByCountry("Japan")
        }
    }

    @Test
    fun `should ignore recipes when name or description is null`() {
        val recipes = listOf(
            createRecipeHelper(name = null, description = "This is a Canadian Meal"),
            createRecipeHelper(name = "Canada", description = null),
            createRecipeHelper(name = "Canada", description = "This is a Meal from Canada"),
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        val result = globalFoodCultureUseCase.getRandomMealsByCountry("Canada")
        assertThat(result).containsExactly(
            createRecipeHelper(name = "Canada", description = "This is a Meal from Canada"),
        )
    }

    companion object {
        const val RECIPE_COLLECTION_SIZE = 50
        const val MAX_RECIPE_COUNT = 20
    }
}