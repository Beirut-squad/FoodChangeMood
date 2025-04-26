import io.mockk.every
import io.mockk.mockk
import org.example.logic.use_case.SearchByNameUseCase
import org.example.logic.RecipesRepository
import org.example.utils.RecipeTestData
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.example.utils.SearchByNameAlgo.Trie
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SearchByNameUseCaseTest {

    private val recipesRepository = mockk<RecipesRepository>()
    private val trie = mockk<Trie>()
    private val searchByNameUseCase = SearchByNameUseCase(recipesRepository, trie)



    @Test
    fun `should return exact match when recipe name matches exactly`() {
        //Given
        val recipes = listOf(
            RecipeTestData.createDefaultRecipe(name = "Pizza", id = "1"),
            RecipeTestData.createDefaultRecipe(name = null, id = "2")
        )
        every { trie.getAllWords() } returns listOf("pizza")
        every { recipesRepository.getAllRecipes() } returns recipes
        //When
        val result = searchByNameUseCase.searchRecipeByName("Pizza")

        // Then
        assertEquals(1, result?.size)
        assertEquals("Pizza", result?.first()?.name)
    }

    @Test
    fun `should return closest match when recipe name does not match exactly`() {
        val recipes = listOf(
            RecipeTestData.createDefaultRecipe(name = "easy strawberry pie with pizazz", id = "1"),
            RecipeTestData.createDefaultRecipe(name = "Pasta", id = "2")
        )

        every { trie.getAllWords() } returns listOf("piza")
        every { recipesRepository.getAllRecipes() } returns recipes
        //when
        val result = searchByNameUseCase.searchRecipeByName("piza")
        //Then
        assertEquals(1, result?.size)
        assertEquals("easy strawberry pie with pizazz", result?.first()?.name)
    }
    @Test
    fun `should throw exception if recipe name is null in fallback`() {
        // Given
       val recipes = listOf(
            RecipeTestData.createDefaultRecipe(name = null, id = "3")
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        every { trie.getAllWords() } returns emptyList()
        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
         searchByNameUseCase.searchRecipeByName("anything")
        }

        assertEquals("Recipe name cannot be null.", exception.message)
    }

    @Test
    fun `should return top 3 closest matches based on Levenshtein distance`() {
        // Given
        val recipes = listOf(
            RecipeTestData.createDefaultRecipe(name = "Pizza", id = "1"),
            RecipeTestData.createDefaultRecipe(name = "Pasta", id = "2"),
            RecipeTestData.createDefaultRecipe(name = "Pineapple Pizza", id = "3")
        )


        every { trie.getAllWords() } returns listOf("piza", "pasta", "pineapple")
        every { recipesRepository.getAllRecipes() } returns recipes
        // When
        val result = searchByNameUseCase.searchRecipeByName("pizza")

        // Then
        assertEquals(3, result?.size)


        val bestMatch = result?.first()?.name
        assertEquals("Pizza", bestMatch)
    }
    @Test
    fun `should return null when input is blank`() {
        //Given
        val recipes = listOf(
            RecipeTestData.createDefaultRecipe(name = "easy strawberry pie with pizazz", id = "1"),
            RecipeTestData.createDefaultRecipe(name = "Pasta", id = "2")
        )

        every { trie.getAllWords() } returns listOf("piza")
        every { recipesRepository.getAllRecipes() } returns recipes
        //When
        val result = searchByNameUseCase.searchRecipeByName("   ")
        //Then
        assertTrue(result.isNullOrEmpty())
    }

    @Test
    fun `should return null when no recipes exist`() {
        every { trie.getAllWords() } returns emptyList()


        val result = searchByNameUseCase.searchRecipeByName("   ")
        assertTrue(result.isNullOrEmpty())
    }


    @Test
    fun `should return null when no recipe name matches`() {
        //Given
        val recipes = listOf(
            RecipeTestData.createDefaultRecipe(name = "Pizza", id = "1")
        )

        every { recipesRepository.getAllRecipes() } returns recipes
        every { trie.getAllWords() } returns listOf("burger")

        // When
        val result = searchByNameUseCase.searchRecipeByName("burger")

        // Then
        assertTrue(result.isNullOrEmpty())
    }

    @Test
    fun `should return closest matches when multiple recipes are available`() {
        //Given
        val recipes = listOf(
            RecipeTestData.createDefaultRecipe(name = "Pizza", id = "1"),
            RecipeTestData.createDefaultRecipe(name = "easy spaghetti bake", id = "2"),
            RecipeTestData.createDefaultRecipe(name = "easy spaghetti", id = "3")
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        every { trie.getAllWords() } returns listOf("spaghetti")
        //When
        val result = searchByNameUseCase.searchRecipeByName("aghe")
        //Then
        assertEquals(2, result?.size)
    }

}
