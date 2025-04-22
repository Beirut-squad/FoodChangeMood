package logic.use_case


import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.RecipesRepository
import org.example.logic.use_case.GymHelperUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GymHelperUseCaseTest {
    private lateinit var repository: RecipesRepository
    private lateinit var gymHelperUseCase: GymHelperUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        gymHelperUseCase = GymHelperUseCase(repository)
    }

    @Test
    fun `should return recipe that contains approximate values when calories and protein are both float `() {
        //Give
        every { repository.getAllRecipes() } returns listOf(
            createGymHelper(5f, 9f),
            createGymHelper(100f, 10f),
            createGymHelper(null, 200f)
        )
        val calories = 9f
        val protein = 10f
        //When
        val result =
            gymHelperUseCase
                .getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(calories, protein)
        //Then
        assertThat(result)
            .containsExactly(
                createGymHelper(5f, 9f))
    }



}