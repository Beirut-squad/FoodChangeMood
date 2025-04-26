package logic.use_case

import io.mockk.every
import io.mockk.mockk
import org.example.error.RecipeNotFoundException
import org.example.logic.RecipesRepository
import org.example.logic.use_case.SweetWithNoEggsUseCase
import org.junit.Assert.assertThrows
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class SweetWithNoEggsUseCaseTest {

    private lateinit var recipesRepository: RecipesRepository
    private lateinit var sweetWithNoEggsUseCase: SweetWithNoEggsUseCase

    @BeforeEach
    fun setup() {
        recipesRepository = mockk(relaxed = true)
        sweetWithNoEggsUseCase = SweetWithNoEggsUseCase(recipesRepository)
    }

    @Test
    fun `should return one random sweet in case-insensitive way when list have multiple sweet with different cases`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForSweetWithNoEgg(
                name = "Coconut Ladoo SWEET",
                description = "Quick, soft, and melt-in-your-mouth coconut balls made with just" +
                        " a few ingredients."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Mango Shrikhand",
                description = "A creamy SwEET, dreamy Indian dessert made from strained yogurt and " +
                        "sweet mango puree. no cooking — just chill and serve!"
            ),
            createRecipeForSweetWithNoEgg(
                name = "Chocolate Fudge Bites sweet with egg",
                description = "Rich, gooey chocolate fudge bites Sweet made with just 3 main ingredients." +
                        "no oven — just quick indulgence."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Peanut Butter Oat Balls with EGG",
                description = "Healthy, chewy, naturally SwEET snacks made with oats and peanut butter." +
                        "No cooking, — just mix and roll!"
            )
        )
        // When
        val result = sweetWithNoEggsUseCase.findSweetsFreeEggs()
        // Then
        assertTrue { result?.name == "Coconut Ladoo SWEET" || result?.name == "Mango Shrikhand" }
    }

    @Test
    fun `should return one random complete sweet when list contains sweets with null name`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForSweetWithNoEgg(
                name = null,
                description = "Quick, soft, and melt-in-your-mouth coconut balls made with just" +
                        " a few ingredients."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Chocolate Fudge Bites",
                description = "Rich, gooey chocolate fudge bites Sweet made with just 3 main ingredients." +
                        "no oven — just quick indulgence."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Peanut Butter Oat Balls sweet",
                description = "Healthy, chewy, naturally SwEET snacks made with oats and peanut butter." +
                        "with egg ,No cooking, — just mix and roll!"
            )
        )
        // When
        val result = sweetWithNoEggsUseCase.findSweetsFreeEggs()
        // Then
        assertEquals(result?.name, "Chocolate Fudge Bites")
    }

    @Test
    fun `should return one random complete sweet when list contains sweets with null description`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForSweetWithNoEgg(
                name = "Coconut Ladoo SWEET",
                description = null
            ),
            createRecipeForSweetWithNoEgg(
                name = "Chocolate Fudge Bites",
                description = "Rich, gooey chocolate fudge bites Sweet made with just 3 main ingredients." +
                        "no oven — just quick indulgence."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Peanut Butter Oat Balls",
                description = "Healthy, chewy, naturally snacks made with oats and peanut butter." +
                        "No cooking, — just mix and roll!"
            )
        )
        // When
        val result = sweetWithNoEggsUseCase.findSweetsFreeEggs()
        // Then
        assertEquals(result?.name, "Chocolate Fudge Bites")
    }

    @Test
    fun `should return one random complete sweet when list contains sweets with null ingredients`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForSweetWithNoEgg(
                name = "Coconut Ladoo SWEET",
                description = "Quick, soft, and melt-in-your-mouth coconut balls made with just a few ingredients." +
                        "Perfect for festivals or quick sweet cravings. no baking, and ready in minutes!",
                ingredients = null
            ),
            createRecipeForSweetWithNoEgg(
                name = "Chocolate Fudge Bites",
                description = "Rich, gooey chocolate fudge bites Sweet made with just 3 main ingredients." +
                        "no oven — just quick indulgence."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Peanut Butter Oat Balls",
                description = "Healthy, chewy, naturally SwEET snacks made with oats and peanut butter." +
                        " No cooking, — just mix and roll!"
            )
        )
        // When
        val result = sweetWithNoEggsUseCase.findSweetsFreeEggs()
        // Then
        assertTrue { result?.name == "Chocolate Fudge Bites" || result?.name == "Peanut Butter Oat Balls" }
    }

    @Test
    fun `should return one random complete sweet when list contains sweets with null steps`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForSweetWithNoEgg(
                name = "Coconut Ladoo",
                description = "Quick, soft, and melt-in-your-mouth coconut balls made with just a few ingredients." +
                        "Perfect for festivals or quick sweet cravings. no baking, and ready in minutes!"
            ),
            createRecipeForSweetWithNoEgg(
                name = "Chocolate Fudge Bites",
                description = "Rich, gooey chocolate fudge bites Sweet made with just 3 main ingredients." +
                        "no oven — just quick indulgence.", steps = null
            ),
            createRecipeForSweetWithNoEgg(
                name = "Peanut Butter Oat Balls with egg",
                description = "Healthy, chewy, naturally SwEET snacks made with oats and peanut butter." +
                        " No cooking, — just mix and roll!"
            )
        )
        // When
        val result = sweetWithNoEggsUseCase.findSweetsFreeEggs()
        // Then
        assertEquals(result?.name, "Coconut Ladoo")
    }

    @Test
    fun `should return one random complete sweet when list contains sweets with null minutes`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForSweetWithNoEgg(
                name = "Coconut Ladoo",
                description = "Quick, soft, and melt-in-your-mouth coconut balls made with just a few ingredients." +
                        "Perfect for festivals or quick sweet cravings. no baking, and ready in minutes!"
            ),
            createRecipeForSweetWithNoEgg(
                name = "Chocolate Fudge Bites",
                description = "Rich, gooey chocolate fudge bites made with just 3 main ingredients." +
                        "no oven — just quick indulgence."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Peanut Butter Oat Balls with",
                description = "Healthy, chewy, naturally SwEET snacks made with oats and peanut butter." +
                        " No cooking, — just mix and roll!", minutes = null
            )
        )
        // When
        val result = sweetWithNoEggsUseCase.findSweetsFreeEggs()
        // Then
        assertEquals(result?.name, "Coconut Ladoo")
    }

    @Test
    fun `should return one random complete sweet when list contains sweets with null tags`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForSweetWithNoEgg(
                name = "Coconut Ladoo",
                description = "Quick, soft, and melt-in-your-mouth coconut balls made with just a few ingredients." +
                        "Perfect for festivals or quick sweet cravings. no baking, and ready in minutes!"
            ),
            createRecipeForSweetWithNoEgg(
                name = "Chocolate Fudge Bites",
                description = "Rich, gooey chocolate fudge bites made with just 3 main ingredients." +
                        "no oven — just quick indulgence."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Peanut Butter Oat Balls",
                description = "Healthy, chewy, naturally SwEET snacks made with oats and peanut butter." +
                        " No cooking, — just mix and roll!", tags = null
            )
        )
        // When
        val result = sweetWithNoEggsUseCase.findSweetsFreeEggs()
        // Then
        assertEquals(result?.name, "Coconut Ladoo")
    }

    @Test
    fun `should return one random complete sweet when list contains sweets with null nutrition`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForSweetWithNoEgg(
                name = "Coconut Ladoo",
                description = "Quick, soft, and melt-in-your-mouth coconut balls made with just a few ingredients." +
                        "Perfect for festivals or quick sweet cravings. no baking, and ready in minutes!",
                nutrition = null
            ),
            createRecipeForSweetWithNoEgg(
                name = "Chocolate Fudge Bites",
                description = "Rich, gooey chocolate fudge bites made with just 3 main ingredients." +
                        "no oven — just quick indulgence."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Peanut Butter Oat Balls sweet",
                description = "Healthy, chewy, naturally SwEET snacks made with oats and peanut butter." +
                        " No cooking, — just mix and roll!"
            )
        )
        // When
        val result = sweetWithNoEggsUseCase.findSweetsFreeEggs()
        // Then
        assertEquals(result?.name, "Peanut Butter Oat Balls sweet")
    }

    @Test
    fun `should throw RecipeNotFoundException when there is no egg-less sweets`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForSweetWithNoEgg(
                name = "Coconut Ladoo with egg",
                description = "Quick, soft, and melt-in-your-mouth coconut balls made with just a few ingredients." +
                        "Perfect for festivals or quick sweet cravings. no baking, and ready in minutes!"
            ),
            createRecipeForSweetWithNoEgg(
                name = "Chocolate Fudge Bites",
                description = "Rich, gooey chocolate fudge bites Sweet made with just 3 main ingredients." +
                        "with 1 egg no oven — just quick indulgence."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Peanut Butter Oat Balls with egg",
                description = "Healthy, chewy, naturally SwEET snacks made with oats and peanut butter." +
                        " No cooking, — just mix and roll!"
            )
        )
        // When & Then
        assertThrows(RecipeNotFoundException::class.java) {
            sweetWithNoEggsUseCase.findSweetsFreeEggs()
        }
    }

    @Test
    fun `should return not null sweet object when list have egg-less sweets`() {
        // Given
        every { recipesRepository.getAllRecipes() } returns listOf(
            createRecipeForSweetWithNoEgg(
                name = "Coconut Ladoo",
                description = "Quick, soft, and melt-in-your-mouth coconut balls made with just a few ingredients." +
                        "Perfect for festivals or quick sweet cravings. no baking, and ready in minutes!"
            ),
            createRecipeForSweetWithNoEgg(
                name = "Chocolate Fudge Bites",
                description = "Rich, gooey chocolate fudge bites Sweet made with just 3 main ingredients." +
                        "with 1 egg no oven — just quick indulgence."
            ),
            createRecipeForSweetWithNoEgg(
                name = "Peanut Butter Oat Balls with egg",
                description = "Healthy, chewy, naturally SwEET snacks made with oats and peanut butter." +
                        " No cooking, — just mix and roll!"
            )
        )
        // When
        val result = sweetWithNoEggsUseCase.findSweetsFreeEggs()
        // Then
        assertNotEquals(result, null)
    }


}