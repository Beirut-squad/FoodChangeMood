package logic.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.RecipesRepository
import org.example.logic.use_case.IraqiMealsUseCase
import org.example.model.Recipe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.runner.Result

class IraqiMealsUseCaseTest{

    private lateinit var recipesRepository: RecipesRepository
    private lateinit var iraqiMealsUseCase: IraqiMealsUseCase

    @BeforeEach
    fun setup(){
        recipesRepository = mockk(relaxed = true)
        iraqiMealsUseCase = IraqiMealsUseCase(recipesRepository)
    }


    @Test
    fun `should return recipes in iraq when there is iraqi in the tags`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipe(name = "iraqi meal 1",tags = listOf("iraqi", "20 min", "easy")),
            createRecipe(name = "egyptian meal",tags = listOf("egypt", "25 min", "lovely")),
            createRecipe(name = "USA meal",tags = listOf("USA", "40 min", "lovely")),
            createRecipe(name = "iraqi meal 2",tags = listOf("IRAQI", "40 min", "lovely"))
        )
        // when
        val result = iraqiMealsUseCase.getIraqiMeals()

        // Then
        assertThat(result).containsExactly(
            createRecipe(name = "iraqi meal 1", tags = listOf("iraqi", "20 min", "easy")),
            createRecipe(name = "iraqi meal 2", tags = listOf("IRAQI", "40 min", "lovely"))
        )

    }

    @Test
    fun `should return recipes in iraq when there is Iraq in the description`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipe(name = "iraqi meal 1", description = "this iraq meal is very good "),
            createRecipe(name = "egyptian meal", description = "egypt is beautiful"),
            createRecipe(name = "USA meal", description = "USA is shit"),
            createRecipe(name = "iraqi meal 2", description = "i love IRAQ so much")
        )

        // When
        val result = iraqiMealsUseCase.getIraqiMeals()

        // Then
        assertThat(result).containsExactly(
            createRecipe(name = "iraqi meal 1", description = "this iraq meal is very good "),
            createRecipe(name = "iraqi meal 2", description = "i love IRAQ so much")
        )
    }

    @Test
    fun `should return empty list when there is no iraqi in the tags`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipe(name = "brazil meal 1",tags = listOf("brazil", "20 min", "easy")),
            createRecipe(name = "egyptian meal",tags = listOf("egypt", "25 min", "lovely")),
            createRecipe(name = "USA meal",tags = listOf("USA", "40 min", "lovely")),
            createRecipe(name = "morocco meal 2",tags = listOf("morocco", "40 min", "lovely"))
        )
        // when
        val result = iraqiMealsUseCase.getIraqiMeals()

        // Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `should return empty list when there is no Iraq in the description`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipe(name = "brazil meal 1", description = "this brazil meal is very good "),
            createRecipe(name = "egyptian meal", description = "egypt is beautiful"),
            createRecipe(name = "USA meal", description = "USA is shit"),
            createRecipe(name = "morocco meal 2", description = "i love morocco so much")
        )

        // When
        val result = iraqiMealsUseCase.getIraqiMeals()

        // Then
        assertThat(result).isEmpty()
    }

}
