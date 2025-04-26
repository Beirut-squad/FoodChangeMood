package ui.features_ui

import io.mockk.every
import io.mockk.mockk
import org.example.logic.use_case.EasyFoodSuggestionUseCase
import org.example.model.Nutrition
import org.example.model.Recipe
import org.example.ui.features_ui.EasyFoodSuggestionUI
import org.example.utils.Colors
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.time.LocalDate
import kotlin.test.assertTrue

class EasyFoodSuggestionUITest {
    private lateinit var easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase
    private lateinit var easyFoodSuggestionUI: EasyFoodSuggestionUI
    private val outputStream = ByteArrayOutputStream()
    private val originalOut = System.out

    @BeforeEach
    fun setup(){
        easyFoodSuggestionUseCase = mockk(relaxed = true)
        easyFoodSuggestionUI = EasyFoodSuggestionUI(easyFoodSuggestionUseCase, Colors())
        System.setOut(PrintStream(outputStream))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun `should return true for print Recipes with specific the format when list contain 2 recipes`(){
        // Given
        every { easyFoodSuggestionUseCase.getTenEasyFoodSuggestions() } returns listOf(

            Recipe("Cake","14258",12,"158236", LocalDate.now(),listOf("","",""),
                Nutrition(12.0f,2.0f,23.0f,25.0f,3.22f,140.0f,23.0f),3,
                listOf("add water to flour","mix it","Done"),"Make a cake",listOf("Milk","Water","Egg"),3) ,

            Recipe("Hot Chocolate","25894",30,"147521", LocalDate.now(),listOf("","",""),
                Nutrition(12.0f,2.0f,23.0f,25.0f,3.22f,140.0f,23.0f)
                ,2,listOf("add milk to water and cacao","mix them"),"Make a Hot Chocolate",listOf("Cacao","Water","Milk","Sugar"),4)
        )
        // When
        easyFoodSuggestionUI.show()
        // Then
        val output = outputStream.toString().trim()
        assertTrue { output.contains("1. Cake - 12 min - 3 ingredients - 3 steps")
                && output.contains("2. Hot Chocolate - 30 min - 4 ingredients - 2 steps")}
    }

    @Test
    fun `should return true for print 0 for num of ingredients and num of steps when recipe ingredients or steps is null`(){
        // Given
        every { easyFoodSuggestionUseCase.getTenEasyFoodSuggestions() } returns listOf(

            Recipe("Cake","14258",12,"158236", LocalDate.now(),listOf("","",""),
                Nutrition(12.0f,2.0f,23.0f,25.0f,3.22f,140.0f,23.0f),3,
                steps = null,"Make a cake",listOf("Milk","Water","Egg"),3) ,

            Recipe("Hot Chocolate","25894",30,"147521", LocalDate.now(),listOf("","",""),
                Nutrition(12.0f,2.0f,23.0f,25.0f,3.22f,140.0f,23.0f)
                ,2,listOf("add milk to water and cacao","mix them"),"Make a Hot Chocolate",
                ingredients = null,4)
        )
        // When
        easyFoodSuggestionUI.show()
        // Then
        val output = outputStream.toString().trim()
        assertTrue { output.contains("1. Cake - 12 min - 3 ingredients - 0 steps")
                && output.contains("2. Hot Chocolate - 30 min - 0 ingredients - 2 steps")}
    }




}