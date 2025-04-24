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


}
