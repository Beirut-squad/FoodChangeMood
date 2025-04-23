package logic.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.RecipesRepository
import org.example.logic.use_case.HealthyRecipesUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
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
        every { recipesRepository.getAllRecipes() } returns listOf(
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
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 1
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
    }

    @DisplayName("should return 2 recipes that can be prepared in 15 minutes or less without null recipe values" +
            "when list contains some nutrition null values")
    @Test
    fun recipeWithOutNullValues() {
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 15, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 14, totalFat = null,
                saturatedFat = null, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = null,
                saturatedFat = null, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 4
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
            createRecipeForHealthyRecipes(
                    "Pizza", minutes = 15, totalFat = 100.0f,
            saturatedFat = 10.0f, carbohydrates = 30.0f
            )
        )
    }

    @Test
    fun `should return 2 recipes that can be prepared in 15 minutes or less when totalFat and name is null`() {
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 15, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
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
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 15, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            )
        )
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when totalFat parameter is null`() {
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 12, totalFat = null,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = null,
                saturatedFat = 20.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
        // When
        val recipesCount = 5
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when saturatedFat parameter is null`() {
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 12, totalFat = 12.0f,
                saturatedFat = null, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f,
                saturatedFat = null, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when saturatedFat and name is null`() {
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 15, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
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
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 15, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            )
        )
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when carbohydrates parameter is null`() {
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 12, totalFat = 12.0f,
                saturatedFat = 22.0f, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f,
                saturatedFat = 14.0f, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when totalFat and carbohydrates is null`() {
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 12, totalFat = null,
                saturatedFat = 22.0f, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 20, totalFat = null,
                saturatedFat = 14.0f, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when minutes, totalFat and carbohydrates is null`() {
        //Given
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
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
    }

    @Test
    fun `should return 1 recipes that can be prepared in 15 minutes or less when name and nutrition is null`() {
        //Given
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
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
        // When
        val recipesCount = 2
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
    }

    @DisplayName("should return recipes sorted from low to high based on (total fat+saturated fat+carbohydrates)" +
            "when list contains multiple recipes")
    @Test
    fun sortedRecipes(){
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes( // 140
                "Pizza", minutes = 15, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 14, totalFat = null,
                saturatedFat = null, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes( // 162
                "Fish", minutes = 8, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes( // 82
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 3
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertTrue(result == listOf(
                createRecipeForHealthyRecipes( // 82
                    "Foul", minutes = 5, totalFat = 50.0f,
                    saturatedFat = 8.0f, carbohydrates = 24.0f
                ),
                createRecipeForHealthyRecipes( // 140
                    "Pizza", minutes = 15, totalFat = 100.0f,
                    saturatedFat = 10.0f, carbohydrates = 30.0f
                ),
                createRecipeForHealthyRecipes( // 162
                    "Fish", minutes = 8, totalFat = 120.0f,
                    saturatedFat = 22.0f, carbohydrates = 20.0f
                )
            )
        )
    }

    @Test
    fun `should return 2 recipes that can be prepared in 15 minutes or less when input count of recipes is 5`(){
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 15, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = null,
                saturatedFat = null, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 30, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 5
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 15, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            )
        )
    }

    @Test
    fun `should return 1 recipe that can be prepared in 15 minutes or less when minutes parameter is null`() {
        //Given
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
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 1
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
    }

    @Test
    fun `should return 2 recipes that can be prepared in 15 minutes or less when recipe name parameter is null`() {
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 12, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 14, totalFat = 25.0f,
                saturatedFat = 20.0f, carbohydrates = 30.0f
            ),
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
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 12, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 14, totalFat = 25.0f,
                saturatedFat = 20.0f, carbohydrates = 30.0f
            )
        )
    }

    @Test
    fun `should return 1 recipe that can be prepared in 15 minutes or less when recipe name and minutes is null`(){
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 12, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f,
                saturatedFat = 20.0f, carbohydrates = 30.0f
            ),
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
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Pizza", minutes = 12, totalFat = 100.0f,
                saturatedFat = 10.0f, carbohydrates = 30.0f
            )
        )
    }

    @Test
    fun `should no return recipes when list doesn't contains recipes can be prepared in 15 minutes or less`(){
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
    fun `should return 2 recipes that can be prepared in 15 minutes or less when list contain null recipe`(){
        //Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForHealthyRecipes(
                null, minutes = null, totalFat = null,
                saturatedFat = null, carbohydrates = null
            ),
            createRecipeForHealthyRecipes(
                "Cake", minutes = 40, totalFat = 25.0f,
                saturatedFat = 20.0f, carbohydrates = 30.0f
            ),
            createRecipeForHealthyRecipes(
                "Fish", minutes = 13, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            ),
        )
        // When
        val recipesCount = 3
        val result = healthyRecipesUseCase.getHealthyRecipes(recipesCount)
        //Then
        assertThat(result).containsExactly(
            createRecipeForHealthyRecipes(
                "Fish", minutes = 13, totalFat = 120.0f,
                saturatedFat = 22.0f, carbohydrates = 20.0f
            ),
            createRecipeForHealthyRecipes(
                "Foul", minutes = 5, totalFat = 50.0f,
                saturatedFat = 8.0f, carbohydrates = 24.0f
            )
        )
    }
}