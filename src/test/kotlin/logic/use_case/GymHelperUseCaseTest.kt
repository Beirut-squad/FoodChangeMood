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

}