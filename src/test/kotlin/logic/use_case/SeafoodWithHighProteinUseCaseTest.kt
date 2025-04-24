package logic.use_case

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

    @Disabled
    @Test
    fun `should return list of seafood protein recipes if found`(){
        // Given
        every { recipesRepository.getAllRecipes() } returns


        // When
        seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()

        // Then


    }




}