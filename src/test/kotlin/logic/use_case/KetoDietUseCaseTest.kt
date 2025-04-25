package logic.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.RecipesRepository
import org.example.logic.use_case.KetoDietUseCase
import org.example.model.Recipe
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class KetoDietUseCaseTest {

    private val recipesRepository: RecipesRepository = mockk(relaxed = true)
    private lateinit var ketoDietUseCase: KetoDietUseCase

    @Test
    fun `suggestKetoRecipe should throw exception when no valid recipes exist`() {
        // Given
        val invalidRecipe = createRecipeWithNutrition(
            totalFat = 5f,
            saturatedFat = 3f,
            sugar = 4f,
            carbs = 5f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `suggestKetoRecipe should return the only valid recipe`() {
        // Given
        val validRecipe = createValidRecipe()
        every { recipesRepository.getAllRecipes() } returns listOf(validRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When
        val result = ketoDietUseCase.suggestKetoRecipe()

        // Then
        assertThat(result).isEqualTo(validRecipe)
    }

    @Test
    fun `suggestKetoRecipe should return any valid recipe when multiple exist`() {
        // Given
        val validRecipes = List(5) { createValidRecipe(id = it.toString()) }
        every { recipesRepository.getAllRecipes() } returns validRecipes
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When
        val result = ketoDietUseCase.suggestKetoRecipe()

        // Then
        assertThat(result).isIn(validRecipes)
    }

    @Test
    fun `should exclude recipe with null totalFat and throw exception`() {
        // Given
        val invalidRecipe = createRecipeWithNutrition(
            totalFat = null,
            saturatedFat = 3f,
            sugar = 4f,
            carbs = 5f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `should exclude recipe with null saturatedFat and throw exception`() {
        // Given
        val invalidRecipe = createRecipeWithNutrition(
            totalFat = 20f,
            saturatedFat = null,
            sugar = 4f,
            carbs = 5f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `should exclude recipe with null sugar and throw exception`() {
        // Given
        val invalidRecipe = createRecipeWithNutrition(
            totalFat = 20f,
            saturatedFat = 3f,
            sugar = null,
            carbs = 5f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `should exclude recipe with null carbs and throw exception`() {
        // Given
        val invalidRecipe = createRecipeWithNutrition(
            totalFat = 20f,
            saturatedFat = 3f,
            sugar = 4f,
            carbs = null
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `should return recipe when keto score is valid`() {
        // Given
        val recipe = createValidRecipe(
            totalFat = 10f,
            saturatedFat = 3f,
            sugar = 4f,
            carbs = 4f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(recipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When
        val result = ketoDietUseCase.suggestKetoRecipe()

        // Given
        assertThat(result).isEqualTo(recipe)
    }

    @Test
    fun `should throw exception when keto score is invalid`() {
        // Given
        val invalidRecipe = createValidRecipe(
            totalFat = 4f,
            saturatedFat = 0f,
            sugar = 0f,
            carbs = 0f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `should exclude recipe with null nutrition and throw exception`() {
        // Given
        val invalidRecipe = Recipe(
            name = "No-Nutrition Recipe",
            id = "123",
            minutes = null,
            contributorId = null,
            submittedDate = null,
            tags = null,
            nutrition = null,
            numberOfSteps = null,
            steps = null,
            description = null,
            ingredients = null,
            numberOfIngredients = null
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `should get recipes when keto use case is initialized`() {
        every { recipesRepository.getAllRecipes() } returns emptyList()

        KetoDietUseCase(recipesRepository)

        verify(exactly = 1) { recipesRepository.getAllRecipes() }
    }

    @Test
    fun `should exclude recipe with invalid totalFat and throw exception`() {
        // Given
        val invalidRecipe = createRecipeWithNutrition(
            totalFat = 5f,
            saturatedFat = 5f,
            sugar = 4f,
            carbs = 5f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `should exclude recipe with invalid saturatedFat and throw exception`() {
        // Given
        val invalidRecipe = createRecipeWithNutrition(
            totalFat = 20f,
            saturatedFat = 2f,
            sugar = 4f,
            carbs = 5f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `should exclude recipe with invalid sugar and throw exception`() {
        // Given
        val invalidRecipe = createRecipeWithNutrition(
            totalFat = 20f,
            saturatedFat = 5f,
            sugar = 5.1f,
            carbs = 5f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `should exclude recipe with invalid carbs and throw exception`() {
        // Given
        val invalidRecipe = createRecipeWithNutrition(
            totalFat = 20f,
            saturatedFat = 5f,
            sugar = 4f,
            carbs = 10.1f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }

    @Test
    fun `should exclude recipe with invalid ketoScore and throw exception`() {
        // Given
        val invalidRecipe = createRecipeWithNutrition(
            totalFat = 10f,
            saturatedFat = 3f,
            sugar = 4.9f,
            carbs = 9.9f
        )
        every { recipesRepository.getAllRecipes() } returns listOf(invalidRecipe)
        ketoDietUseCase = KetoDietUseCase(recipesRepository)

        // When && Then
        assertThrows<Exception> { ketoDietUseCase.suggestKetoRecipe() }
    }
}