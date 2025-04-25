package logic.use_case


import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.error.RecipeNotFoundException
import org.example.logic.RecipesRepository
import org.example.logic.use_case.GymHelperUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GymHelperUseCaseTest {
    private lateinit var repository: RecipesRepository
    private lateinit var gymHelperUseCase: GymHelperUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        gymHelperUseCase = GymHelperUseCase(repository)
    }

    @Test
    fun `should return recipe that contains approximate values when calories and protein are both positive float `() {
        //Give
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(5f, 9f),
            createGymHelper(100f, 10f),
            createGymHelper(60f, 200f)
        )
        val calories = 9f
        val protein = 10f
        //When
        val result =
            gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        //Then
        assertThat(result)
            .containsExactly(
                createGymHelper(5f, 9f)
            )
    }

    @Test
    fun `should return an exception when calories and protein are null`() {
        //Give
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(null, null),
            createGymHelper(null, null),
            createGymHelper(null, null)
        )
        val calories = 9f
        val protein = 10f
        //When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when only protein is null`() {
        //Give
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(9f, null)
        )
        val calories = 9f
        val protein = 10f
        //When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when only calories is null`() {
        //Give
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(null, 10f)
        )
        val calories = 9f
        val protein = 10f
        //When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }

    }

    @Test
    fun `should return an exception when search by calories and protein in an empty list`() {
        //Give
        every { repository.getAllRecipes() } returns emptyList()
        val calories = 9f
        val protein = 10f
        //When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }
    @Test
    fun `should return an exception when calories in are negative value`() {
        //Give
        every { repository.getAllRecipes() } returns emptyList()
        val calories = -9f
        val protein = 10f
        //When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when protein in are negative value`() {
        //Give
        every { repository.getAllRecipes() } returns emptyList()
        val calories = 9f
        val protein = -10f
        //When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when calories is zero`() {
        //Give
        every { repository.getAllRecipes() } returns emptyList()
        val calories = 0f
        val protein = 10f
        //When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when protein is zero`() {
        //Give
        every { repository.getAllRecipes() } returns emptyList()
        val calories = 9f
        val protein = 0f
        //When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when both protein and calories are zeroes`() {
        //Give
        every { repository.getAllRecipes() } returns emptyList()
        val calories = 0f
        val protein = 0f
        //When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when protein is negative value`() {
        // Given
        every { repository.getAllRecipes() } returns emptyList()
        val calories = 10f
        val protein = -40f

        // When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when calories is negative value`() {
        // Given
        every { repository.getAllRecipes() } returns emptyList()
        val calories = -20f
        val protein = 40f

        // When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when calories and protein are negative value`() {
        // Given
        every { repository.getAllRecipes() } returns emptyList()
        val calories = -20f
        val protein = -40f

        // When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when calories less than zero`() {
        // Given
        every { repository.getAllRecipes() } returns emptyList()
        val calories = 0.20f
        val protein = -40f

        // When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when protein less than zero`() {
        // Given
        every { repository.getAllRecipes() } returns emptyList()
        val calories = 20f
        val protein = 0.40f

        // When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should return an exception when both calories and protein are less than zero`() {
        // Given
        every { repository.getAllRecipes() } returns emptyList()
        val calories = 0.20f
        val protein = -40f

        // When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        }
    }

    @Test
    fun `should not include recipes with null nutrition`() {
        // Given
        every { repository.getAllRecipes() } returns listOf(
            createRecipeWithNullNutritionHelper(),
            createGymHelper(5f, 5f)
        )

        // When
        val result = gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(5f, 5f)

        // Then
        assertThat(result).containsNoneOf(createRecipeWithNullNutritionHelper(), createRecipeWithNullNutritionHelper())
    }

    @Test
    fun `should not include recipes with minimum default value calories`() {
        // Given
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(5f, 5f),
            createGymHelper(Float.MIN_VALUE, 5f)
        )

        // When
        val result = gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(5f, 5f)

        // Then
        assertThat(result).containsNoneOf(createGymHelper(Float.MIN_VALUE, 5f), createGymHelper(Float.MIN_VALUE, 5f))
    }

    @Test
    fun `should not include recipes with minimum float value protein`() {
        // Given
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(5f, 5f),
            createGymHelper(5f, Float.MIN_VALUE)
        )

        // When
        val result = gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(5f, 5f)

        // Then
        assertThat(result).containsNoneOf(createGymHelper(5f, Float.MIN_VALUE), createGymHelper(5f, Float.MIN_VALUE))
    }

    @Test
    fun `should not include recipes with minimum float value protein and calories`() {
        // Given
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(Float.MIN_VALUE, Float.MIN_VALUE),
            createGymHelper(Float.MIN_VALUE, Float.MIN_VALUE)
        )

        // When && Then
        assertThrows<RecipeNotFoundException> {
            gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(5f, 5f)
        }
    }

    @Test
    fun `should exclude recipes with protein value outside range`() {
        // Given
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(50f, 5f),
            createGymHelper(50f, 50f)
        )

        // When
        val result = gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(50f, 50f)

        // Then
        assertThat(result).containsNoneOf(createGymHelper(50f, 5f), createGymHelper(50f, 5f))
    }

    @Test
    fun `should exclude recipes with calories value outside range`() {
        // Given
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(5f, 50f),
            createGymHelper(50f, 50f)
        )

        // When
        val result = gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(50f, 50f)

        // Then
        assertThat(result).containsNoneOf(createGymHelper(5f, 50f), createGymHelper(5f, 50f))
    }

    @Test
    fun `should exclude recipes with calories and protein values outside range`() {
        // Given
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(5f, 5f),
            createGymHelper(50f, 50f)
        )

        // When
        val result = gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(50f, 50f)

        // Then
        assertThat(result).containsNoneOf(createGymHelper(5f, 5f), createGymHelper(5f, 5f))
    }

    @Test
    fun `should include recipes with right amount of calories and protein`() {
        // Given
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(5f, 5f),
            createGymHelper(10f, 4f)
        )

        // When
        val result = gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(4f, 4f)

        // Then
        assertThat(result).contains(createGymHelper(5f, 5f))
        assertThat(result).contains(createGymHelper(10f, 4f))
    }
}