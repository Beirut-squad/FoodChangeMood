package logic

import org.example.logic.Validator
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidatorTest {
    private var validator = Validator()

    @Test
    fun `should return true when gym helper use case called and add protein and calories`() {
        //Given
        val calories = "30.0"
        val protein = "22.0"
        //When
        val result = validator.validateGymHelperInput(calories, protein)
        //Then
        assertTrue { result }

    }

    @Test
    fun `should return false when input is number and bigger than zero `() {
        //Given
        val input = 0
        //When
        val result = validator.validateRecipesCountInput(input)
        //Then
        assertFalse { result }
    }

    @Test
    fun `should return false when gym helper use case calories input is not number `() {
        //Given
        val calories = "string"
        val protein = "22.0"
        //When
        val result = validator.validateGymHelperInput(calories, protein)
        //Then
        assertFalse { result }

    }

    @Test
    fun `should return false when gym helper use case protein input is not number `() {
        //Given
        val calories = "22.3"
        val protein = "float"
        //When
        val result = validator.validateGymHelperInput(calories, protein)
        //Then
        assertFalse { result }

    }

    @Test
    fun `should return false when gym helper use case protein and calories inputs are not number `() {
        //Given
        val calories = "I am not a number"
        val protein = "float"
        //When
        val result = validator.validateGymHelperInput(calories, protein)
        //Then
        assertFalse { result }

    }

    @Test
    fun `should return true when input is alphabetic `() {
        //Given
        val input = "Egypt"
        //When
        val result = validator.validateIsAlphabetic(input)
        //Then
        assertTrue { result }
    }

    @Test
    fun `should return false when input is not alphabetic `() {
        //Given
        val input = "22"
        //When
        val result = validator.validateIsAlphabetic(input)
        //Then
        assertFalse { result }
    }

    @Test
    fun `should return true when input is number and bigger than zero `() {
        //Given
        val input = 100
        //When
        val result = validator.validateRecipesCountInput(input)
        //Then
        assertTrue { result }
    }

    @Test
    fun `should return false when input is number equals zero `() {
        //Given
        val input = 0
        //When
        val result = validator.validateRecipesCountInput(input)
        //Then
        assertFalse { result }
    }

    @Test
    fun `should return false when input is number is negative `() {
        //Given
        val input = -12
        //When
        val result = validator.validateRecipesCountInput(input)
        //Then
        assertFalse { result }
    }



}
