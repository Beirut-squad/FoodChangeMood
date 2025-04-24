package logic.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.RecipesRepository
import org.example.logic.use_case.HealthyRecipesUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class HealthyRecipesUseCaseTest {
    private lateinit var recipesRepository: RecipesRepository
    private lateinit var healthyRecipesUseCase: HealthyRecipesUseCase

    @BeforeEach
    fun setup() {
        recipesRepository = mockk(relaxed = true)
        healthyRecipesUseCase = HealthyRecipesUseCase(recipesRepository)
    }

    @Test
    fun `should return 1 recipe that can be prepared in 15 minutes or less when input count of recipes is 1`() {
        //Given
        val expectedRecipe = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            expectedRecipe,
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 15, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 150.0f,
                saturatedFat = 14.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
        )
        // When
        val recipesCount = 1
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipe)
    }

    @Test
    fun `should return 3 recipes that can be prepared in 15 minutes or less when list contains some nutrition null values`() {
        //Given
        val expectedRecipeOne = createRecipeForHealthyRecipes(
            "Pizza", minutes = 15, totalFat = 100.0f,
            saturatedFat = 10.0f, carbohydrates = 30.0f
        )
        val expectedRecipeTwo = createRecipeForHealthyRecipes(
            "Cake", minutes = 14, totalFat = null,
            saturatedFat = null, carbohydrates = null
        )
        val expectedRecipeThree = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            expectedRecipeOne, expectedRecipeTwo, expectedRecipeThree,
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = null,
                saturatedFat = null, carbohydrates = null
            )
        )
        // When
        val recipesCount = 4
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipeOne, expectedRecipeTwo, expectedRecipeThree)
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when totalFat and name is null`() {
        //Given
        val expectedRecipe = createRecipeForHealthyRecipes(
            "Pizza", minutes = 15, totalFat = 100.0f,
            saturatedFat = 10.0f, carbohydrates = 30.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            expectedRecipe,
            createRecipeForHealthyRecipes(
                null, minutes = 40, totalFat = null,
                saturatedFat = 14.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                null, minutes = 5, totalFat = null,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 4
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipe)
    }

    @Test
    fun `should return 2 recipes that can be prepared in 15 minutes or less when totalFat parameter is null`() {
        //Given
        val expectedRecipeOne = createRecipeForHealthyRecipes(
            "Pizza", minutes = 12, totalFat = null,
            saturatedFat = 10.0f, carbohydrates = 30.0f
        )
        val expectedRecipeTwo = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            expectedRecipeOne, expectedRecipeTwo,
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
        // When
        val recipesCount = 5
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipeOne, expectedRecipeTwo)
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when totalFat and minutes parameter is null`() {
        //Given
        val expectedRecipe = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = null, totalFat = null,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = null, totalFat = null,
                saturatedFat = 20.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ), expectedRecipe
        )
        // When
        val recipesCount = 5
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipe)
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when saturatedFat parameter is null`() {
        //Given
        val expectedRecipeOne = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        val expectedRecipeTwo = createRecipeForHealthyRecipes(
            "Pizza", minutes = 12, totalFat = 12.0f,
            saturatedFat = null, carbohydrates = 30.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f,
                saturatedFat = null, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            expectedRecipeOne, expectedRecipeTwo
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            expectedRecipeOne, expectedRecipeTwo
        )
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when saturatedFat and name is null`() {
        //Given
        val expectedRecipe = createRecipeForHealthyRecipes(
            "Pizza", minutes = 15, totalFat = 100.0f,
            saturatedFat = 10.0f, carbohydrates = 30.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            expectedRecipe,
            createRecipeForHealthyRecipes(
                null, minutes = 40, totalFat = 15.0f,
                saturatedFat = null, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                null, minutes = 5, totalFat = 22.0f,
                saturatedFat = null, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 4
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result.size == 1)
        assertThat(result).containsExactly(expectedRecipe)
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when saturatedFat and minutes is null`() {
        //Given
        val expectedRecipe = createRecipeForHealthyRecipes(
            "Pizza", minutes = 15, totalFat = 100.0f,
            saturatedFat = 10.0f, carbohydrates = 30.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            expectedRecipe,
            createRecipeForHealthyRecipes(
                "Cake", minutes = null, totalFat = 15.0f,
                saturatedFat = null, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                "Foul", minutes = null, totalFat = 22.0f,
                saturatedFat = null, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 4
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result.size == 1)
        assertThat(result).containsExactly(expectedRecipe)
    }

    @Test
    fun `should return 2 recipes that can be prepared in 15 minutes or less when carbohydrates parameter is null`() {
        //Given
        val expectedRecipeOne = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        val expectedRecipeTwo = createRecipeForHealthyRecipes(
            "Pizza", minutes = 12, totalFat = 12.0f,
            saturatedFat = 22.0f, carbohydrates = null
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            expectedRecipeOne,
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f,
                saturatedFat = 14.0f, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            expectedRecipeTwo
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipeOne, expectedRecipeTwo)
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when carbohydrates and name parameter is null`() {
        //Given
        val expectedRecipe = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                null, minutes = 12, totalFat = 12.0f,
                saturatedFat = 22.0f, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                null, minutes = 40, totalFat = 25.0f,
                saturatedFat = 14.0f, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            expectedRecipe
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipe)
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when minutes, totalFat and carbohydrates is null`() {
        //Given
        val expectedRecipe = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = null, totalFat = null,
                saturatedFat = null, carbohydrates = 24.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = null, totalFat = null,
                saturatedFat = null, carbohydrates = 25.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            expectedRecipe
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipe)
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when name and nutrition is null`() {
        //Given
        val expectedRecipe = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                null, minutes = 12, totalFat = null,
                saturatedFat = null, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                null, minutes = 20, totalFat = null,
                saturatedFat = null, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            expectedRecipe
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipe)
    }

    @Test
    fun `should return recipes sorted from low to high based on (total fat+saturated fat+carbohydrates) when list contains multiple recipes`() {
        //Given
        val expectedRecipeOne = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        val expectedRecipeTwo = createRecipeForHealthyRecipes(
            "Pizza", minutes = 15, totalFat = 100.0f,
            saturatedFat = 10.0f, carbohydrates = 30.0f
        )
        val expectedRecipeThree = createRecipeForHealthyRecipes(
            "Fish", minutes = 8, totalFat = 120.0f,
            saturatedFat = 22.0f, carbohydrates = 20.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            expectedRecipeTwo,
            createRecipeForHealthyRecipes(
                "Cake", minutes = 14, totalFat = null,
                saturatedFat = null, carbohydrates = 30.0f
            ),
            expectedRecipeThree,
            expectedRecipeOne,
        )
        // When
        val recipesCount = 3
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertTrue(
            result == listOf(
                expectedRecipeOne, // 82
                expectedRecipeTwo, // 140
                expectedRecipeThree // 162
            )
        )
    }

    @Test
    fun `should return 2 recipes that can be prepared in 15 minutes or less when input count of recipes is 5`() {
        //Given
        val expectedRecipeOne = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        val expectedRecipeTwo = createRecipeForHealthyRecipes(
            "Pizza", minutes = 15, totalFat = 100.0f,
            saturatedFat = 10.0f, carbohydrates = 30.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            expectedRecipeTwo,
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 60.0f,
                saturatedFat = 40.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            expectedRecipeOne
        )
        // When
        val recipesCount = 5
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipeOne, expectedRecipeTwo)
    }

    @Test
    fun `should return 1 recipe that can be prepared in 15 minutes or less when minutes parameter is null`() {
        //Given
        val expectedRecipe = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = null, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f,
                saturatedFat = 20.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            expectedRecipe
        )
        // When
        val recipesCount = 1
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipe)
    }

    @Test
    fun `should return 2 recipes that can be prepared in 15 minutes or less when recipe name parameter is null`() {
        //Given
        val expectedRecipeOne = createRecipeForHealthyRecipes(
            "Pizza", minutes = 12, totalFat = 100.0f,
            saturatedFat = 10.0f, carbohydrates = 30.0f
        )
        val expectedRecipeTwo = createRecipeForHealthyRecipes(
            "Cake", minutes = 14, totalFat = 25.0f,
            saturatedFat = 20.0f, carbohydrates = 30.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            expectedRecipeOne, expectedRecipeTwo,
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                null, minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipeOne, expectedRecipeTwo)
    }

    @Test
    fun `should return 1 recipe that can be prepared in 15 minutes or less when recipe name and minutes is null`() {
        //Given
        val expectedRecipe = createRecipeForHealthyRecipes(
            "Pizza", minutes = 12, totalFat = 100.0f,
            saturatedFat = 10.0f, carbohydrates = 30.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f,
                saturatedFat = 20.0f, carbohydrates = 30.0f
            ),
            expectedRecipe,
            createRecipeForHealthyRecipes(
                null, minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                null, minutes = null, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipe)
    }

    @Test
    fun `should no return recipes when list doesn't contains recipes can be prepared in 15 minutes or less`() {
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 17, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f,
                saturatedFat = 20.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                "Foul", minutes = 25, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
        // When
        val recipesCount = 7
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return 2 recipes that can be prepared in 15 minutes or less when list contain null recipe`() {
        //Given
        val expectedRecipeOne = createRecipeForHealthyRecipes(
            "Fish", minutes = 13, totalFat = 120.0f,
            saturatedFat = 22.0f, carbohydrates = 20.0f
        )
        val expectedRecipeTwo = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f,
            saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                null, minutes = null, totalFat = null,
                saturatedFat = null, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f,
                saturatedFat = 20.0f, carbohydrates = 30.0f
            ),
            expectedRecipeOne, expectedRecipeTwo
        )
        // When
        val recipesCount = 3
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(expectedRecipeOne, expectedRecipeTwo)
    }

    @Test
    fun `should all recipes returned has not null nutrition when list have some recipes with nutrition null`() {
        //Given
        val nullNutritionRecipe = createRecipeForHealthyRecipes(
            "Fish", minutes = 13, nutrition = null, totalFat = null, saturatedFat = null, carbohydrates = null
        )
        val notNullNutritionRecipe = createRecipeForHealthyRecipes(
            "Foul", minutes = 5, totalFat = 50.0f, saturatedFat = 8.0f, carbohydrates = 24.0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f, saturatedFat = 20.0f, carbohydrates = 30.0f
            ),
            nullNutritionRecipe,
            notNullNutritionRecipe
        )
        // When
        val recipesCount = 3
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(notNullNutritionRecipe)
    }

}