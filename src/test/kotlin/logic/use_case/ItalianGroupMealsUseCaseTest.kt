package logic.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.error.RecipeNotFoundException
import org.example.logic.RecipesRepository
import org.example.logic.use_case.ItalianGroupMealsUseCase
import org.example.model.Recipe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows

class ItalianGroupMealsUseCaseTest {

    private lateinit var recipesRepository: RecipesRepository
    private lateinit var italianGroupMealsUseCase: ItalianGroupMealsUseCase

    @BeforeEach
    fun setup() {
        this.recipesRepository = mockk(relaxed = true)
        this.italianGroupMealsUseCase = ItalianGroupMealsUseCase(this.recipesRepository)
    }

    @Test
    fun `should return only italian recipes with both italian and large groups tags`() {
        // Given
        val recipes = listOf(
            Recipe(name = "gaza recipes", tags = listOf("italians", "gaza")),
            Recipe(name = "italian recipes", tags = listOf("italian", "for-large-groups", "gaza")),
            Recipe(name = "italian recipes 2", tags = listOf("italian", "for-large-groups", "palestine")),
            Recipe(name = "iraqi recipes", tags = listOf("iraqi")),
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        // when
        val result = italianGroupMealsUseCase.getItalianGroupMeals()

        // Then
        assertThat(result).containsExactly(
            recipes[1], recipes[2]
        )
    }

    @Test
    fun `should throw RecipeNotFoundException when there are no recipes`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns emptyList()

        // when && Then
        assertThrows<RecipeNotFoundException> {
            italianGroupMealsUseCase.getItalianGroupMeals()
        }
    }

    @Test
    fun `should throw RecipeNotFoundException when no recipes have both italian and for-large-groups tags`() {
        // Given
        val recipes = listOf(
            Recipe(name = "gaza recipes", tags = listOf("for-large-groups", "iraqi", "gaza")),
            Recipe(name = "italian recipes", tags = listOf("italian", "iraqi", "gaza")),
            Recipe(name = "iraqi recipes", tags = listOf("iraqi")),
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        // when && Then
        assertThrows<RecipeNotFoundException> {
            italianGroupMealsUseCase.getItalianGroupMeals()
        }
    }

    @Test
    fun `should return only italian recipes when handle case-insensitive tags`() {
        // Given
        val recipes = listOf(
            Recipe(name = "gaza recipes", tags = listOf("italians", "gaza")),
            Recipe(name = "italian recipes 1", tags = listOf("ITALIAN", "for-large-groups", "gaza")),
            Recipe(name = "italian recipes 2", tags = listOf("italian", "FOR-LARGE-GROUPS", "palestine")),
            Recipe(name = "italian recipes 3", tags = listOf("Italian", "For-large-groups", "palestine")),
            Recipe(name = "iraqi recipes", tags = listOf("iraqi")),
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        // when
        val result = italianGroupMealsUseCase.getItalianGroupMeals()

        // Then
        assertThat(result).containsExactly(
            recipes[1], recipes[2], recipes[3]
        )
    }

    @Test
    fun `should return only italian recipes when have only quotation signal in tags`() {
        // Given
        val recipes = listOf(
            Recipe(name = "gaza recipes", tags = listOf("italians", "gaza")),
            Recipe(name = "italian recipes 1", tags = listOf("'ITALIAN'", "for-large-groups", "gaza")),
            Recipe(name = "italian recipes 2", tags = listOf("#italian$", "FOR-LARGE-GROUPS", "palestine")),
            Recipe(name = "italian recipes 3", tags = listOf("not-Italian'", "'For-large-groups'", "'palestine'")),
            Recipe(name = "iraqi recipes", tags = listOf("iraqi")),
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        // when
        val result = italianGroupMealsUseCase.getItalianGroupMeals()

        // Then
        assertThat(result).containsExactly(recipes[1])
    }

    @Test
    fun `should return only italian recipes and handle null tags`() {
        // Given
        val recipes = listOf(
            Recipe(name = "italian recipes", tags = null),
            Recipe(name = "italian recipes 1", tags = listOf("'ITALIAN'", "for-large-groups", "gaza")),
            Recipe(name = "iraqi recipes", tags = listOf("iraqi")),
        )
        every { recipesRepository.getAllRecipes() } returns recipes

        // when
        val result = italianGroupMealsUseCase.getItalianGroupMeals()

        // Then
        assertThat(result).containsExactly(
            recipes[1],
        )
    }

}