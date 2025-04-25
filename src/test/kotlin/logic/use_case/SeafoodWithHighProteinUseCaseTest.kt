package logic.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.RecipesRepository
import org.example.logic.use_case.SeafoodWithHighProteinUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SeafoodWithHighProteinUseCaseTest{
    private lateinit var recipesRepository: RecipesRepository
    private lateinit var seafoodWithHighProteinUseCase: SeafoodWithHighProteinUseCase


    @BeforeEach
    fun setup(){
        recipesRepository = mockk(relaxed = true)
        seafoodWithHighProteinUseCase = SeafoodWithHighProteinUseCase(recipesRepository)
    }


    @Test
    fun `should return sorted list of seafood protein recipes if seafood tag was found in the tags of such recipe`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipe("butterfly shrimp" , tags = listOf("seafood" , "sea" ,"shrimp"), nutrition = createNutrition(protein = 30f)),
            createRecipe("chicken soup" , tags = listOf("chicken" , "good" ,"30-min")),
            createRecipe("tuna sandwich" , tags = listOf("tasty" , "tuna" ,"seafood") , nutrition = createNutrition(protein = 40f)),
            createRecipe("easy pizza" , tags = listOf("pizza" , "easy" ,"15-min")),
        )

        // When
        val result = seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()

        // Then
        assertThat(result).containsExactly(
            createRecipe("tuna sandwich" , tags = listOf("tasty" , "tuna" ,"seafood") , nutrition = createNutrition(protein = 40f)),
            createRecipe("butterfly shrimp" , tags = listOf("seafood" , "sea" ,"shrimp"), nutrition = createNutrition(protein = 30f)),
            )
    }

    @Test
    fun `should return empty list when nutrition facts is null`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipe("butterfly shrimp" , tags = listOf("seafood" , "sea" ,"shrimp")),
            createRecipe("chicken soup" , tags = listOf("chicken" , "good" ,"30-min")),
            createRecipe("tuna sandwich" , tags = listOf("tasty" , "tuna" ,"seafood") ,),
            createRecipe("easy pizza" , tags = listOf("pizza" , "easy" ,"15-min")),
        )

        // When
        val result = seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return empty list when protein facts is null`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipe("butterfly shrimp" , tags = listOf("seafood" , "sea" ,"shrimp"), nutrition = createNutrition(sugar = 20f)),
            createRecipe("chicken soup" , tags = listOf("chicken" , "good" ,"30-min")),
            createRecipe("tuna sandwich" , tags = listOf("tasty" , "tuna" ,"seafood") , nutrition = createNutrition(sugar = 10f)),
            createRecipe("easy pizza" , tags = listOf("pizza" , "easy" ,"15-min")),
        )

        // When
        val result = seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return list of seafood protein recipes if SEAFOOD tag was found in the tags of such recipe`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipe("butterfly shrimp" , tags = listOf("SEAFOOD" , "sea" ,"shrimp"), nutrition = createNutrition(protein = 30f)),
            createRecipe("chicken soup" , tags = listOf("chicken" , "good" ,"30-min")),
            createRecipe("tuna sandwich" , tags = listOf("tasty" , "tuna" ,"SEAFOOD") , nutrition = createNutrition(protein = 40f)),
            createRecipe("easy pizza" , tags = listOf("pizza" , "easy" ,"15-min")),
        )

        // When
        val result = seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()

        // Then
        assertThat(result).containsExactly(
            createRecipe("tuna sandwich" , tags = listOf("tasty" , "tuna" ,"SEAFOOD") , nutrition = createNutrition(protein = 40f)),
            createRecipe("butterfly shrimp" , tags = listOf("SEAFOOD" , "sea" ,"shrimp"), nutrition = createNutrition(protein = 30f)),
        )
    }

    @Test
    fun `should return empty list when when there is no seafood recipes`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipe("butterfly meat" , tags = listOf("meat" , "non" ,"shrimp") , nutrition = createNutrition(protein = 30f)),
            createRecipe("chicken soup" , tags = listOf("chicken" , "good" ,"30-min")),
            createRecipe("shawerma sandwich" , tags = listOf("tasty" , "shawerma" ,"chicken") ),
            createRecipe("easy pizza" , tags = listOf("pizza" , "easy" ,"15-min")),
        )

        // When
        val result = seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should discard recipe if its tag is null`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipe("butterfly shrimp" , nutrition = createNutrition(protein = 30f)),
            createRecipe("chicken soup" , tags = listOf("chicken" , "good" ,"30-min")),
            createRecipe("tuna sandwich" , tags = listOf("tasty" , "tuna" ,"seafood") , nutrition = createNutrition(protein = 40f)),
            createRecipe("easy pizza" , tags = listOf("pizza" , "easy" ,"15-min")),
        )

        // When
        val result = seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()

        // Then
        assertThat(result).containsExactly(
            createRecipe("tuna sandwich" , tags = listOf("tasty" , "tuna" ,"seafood") , nutrition = createNutrition(protein = 40f)),
        )
    }





}