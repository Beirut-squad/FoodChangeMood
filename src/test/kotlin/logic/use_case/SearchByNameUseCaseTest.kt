import io.mockk.every
import io.mockk.mockk
import org.example.logic.use_case.SearchByNameUseCase
import org.example.logic.RecipesRepository
import org.example.model.Recipe
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import org.example.utils.SearchByNameAlgo.Trie
import kotlin.test.assertTrue

class SearchByNameUseCaseTest {

    private val recipesRepository = mockk<RecipesRepository>()
    private val trie = mockk<Trie>()
    private val searchByNameUseCase = SearchByNameUseCase(recipesRepository, trie)

    @Test
    fun `should return exact match when recipe name matches exactly`() {
        //Given
        val recipes = listOf(
            Recipe(name = "Pizza", id = "1", minutes = 30, contributorId = "123",
                submittedDate = null, tags = null, nutrition = null,
                numberOfSteps = null, steps = null, description = null,
                ingredients = null, numberOfIngredients = null),
            Recipe(name = "Pasta", id = "2", minutes = 25, contributorId = "124",
                submittedDate = null, tags = null, nutrition = null, numberOfSteps = null,
                steps = null, description = null, ingredients = null, numberOfIngredients = null)
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        every { trie.getAllWords() } returns listOf("pizza")

        //When
        val result = searchByNameUseCase.searchRecipeByName("Pizza")

        // Then
        assertEquals(1, result?.size)
        assertEquals("Pizza", result?.first()?.name)
    }

    @Test
    fun `should return closest match when recipe name does not match exactly`() {
        val recipes = listOf(
            //Given
            Recipe(name = "easy strawberry pie with pizazz", id = "1",
                minutes = 30, contributorId = "123", submittedDate = null,
                tags = null, nutrition = null, numberOfSteps = null,
                steps = null, description = null, ingredients = null,
                numberOfIngredients = null),
            Recipe(name = "Pasta", id = "2", minutes = 25, contributorId = "124",
                submittedDate = null, tags = null, nutrition = null, numberOfSteps = null,
                steps = null, description = null, ingredients = null, numberOfIngredients = null)
        )

        every { recipesRepository.getAllRecipes() } returns recipes
        every { trie.getAllWords() } returns listOf("piza")
        //when
        val result = searchByNameUseCase.searchRecipeByName("piza")
        //Then
        assertEquals(1, result?.size)
        assertEquals("easy strawberry pie with pizazz", result?.first()?.name)
    }
    @Test
    fun `should throw exception if recipe name is null in fallback`() {
        //  Given
        val recipes = listOf(
            Recipe(name = null, id = "3", minutes = 10, contributorId = "997",
                submittedDate = null, tags = null, nutrition = null, numberOfSteps = null,
                steps = null, description = null, ingredients = null, numberOfIngredients = null)
        )
        every { recipesRepository.getAllRecipes() } returns recipes
        every { trie.getAllWords() } returns emptyList()
        //When and Then
        try {
            searchByNameUseCase.searchRecipeByName("anything")
        } catch (e: IllegalArgumentException) {
            assertEquals("Recipe name cannot be null.", e.message)
        }
    }

    @Test
    fun `should return top 3 closest matches based on Levenshtein distance`() {
        // Given
        val recipes = listOf(
            Recipe(name = "Pizza", id = "1", minutes = 30, contributorId = "123",
                submittedDate = null, tags = null, nutrition = null, numberOfSteps = null,
                steps = null, description = null, ingredients = null, numberOfIngredients = null),
            Recipe(name = "Pasta", id = "2", minutes = 25, contributorId = "124",
                submittedDate = null, tags = null, nutrition = null, numberOfSteps = null,
                steps = null, description = null, ingredients = null, numberOfIngredients = null),
            Recipe(name = "Pineapple Pizza", id = "3", minutes = 20, contributorId = "125",
                submittedDate = null, tags = null, nutrition = null, numberOfSteps = null,
                steps = null, description = null, ingredients = null, numberOfIngredients = null)
        )


        every { recipesRepository.getAllRecipes() } returns recipes
        every { trie.getAllWords() } returns listOf("piza", "pasta", "pineapple")
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
            Recipe(name = "easy strawberry pie with pizazz", id = "1",
                minutes = 30, contributorId = "123", submittedDate = null,
                tags = null, nutrition = null, numberOfSteps = null,
                steps = null, description = null, ingredients = null,
                numberOfIngredients = null),
            Recipe(name = "Pasta", id = "2", minutes = 25, contributorId = "124",
                submittedDate = null, tags = null, nutrition = null, numberOfSteps = null,
                steps = null, description = null, ingredients = null, numberOfIngredients = null)
        )

        every { recipesRepository.getAllRecipes() } returns recipes
        every { trie.getAllWords() } returns listOf("piza")
        //When
        val result = searchByNameUseCase.searchRecipeByName("   ")
        //Then
        assertTrue(result?.isEmpty() == true)
    }

    @Test
    fun `should return null when no recipes exist`() {
        every { recipesRepository.getAllRecipes() } returns emptyList()
        every { trie.getAllWords() } returns emptyList()

        val result = searchByNameUseCase.searchRecipeByName("   ")
        assertTrue(result?.isEmpty() == true)
    }


    @Test
    fun `should return null when no recipe name matches`() {
        //Given
        val recipes = listOf(
            Recipe(name = "Pizza", id = "1", minutes = 30, contributorId = "123",
                submittedDate = null, tags = null, nutrition = null, numberOfSteps = null,
                steps = null, description = null, ingredients = null, numberOfIngredients = null)
        )


        every { recipesRepository.getAllRecipes() } returns recipes
        every { trie.getAllWords() } returns listOf("burger")

        // When
        val result = searchByNameUseCase.searchRecipeByName("burger")

        // Then
        assertTrue(result?.isEmpty() == true)
    }

    @Test
    fun `should return closest matches when multiple recipes are available`() {
        //Given
        val recipes = listOf(
            Recipe(name = "Pizza", id = "1", minutes = 30, contributorId = "123", submittedDate = null, tags = null, nutrition = null, numberOfSteps = null, steps = null, description = null, ingredients = null, numberOfIngredients = null),
            Recipe(name = "easy spaghetti bake", id = "2", minutes = 25, contributorId = "124", submittedDate = null, tags = null, nutrition = null, numberOfSteps = null, steps = null, description = null, ingredients = null, numberOfIngredients = null),
            Recipe(name = "easy spaghetti", id = "3", minutes = 45, contributorId = "125", submittedDate = null, tags = null, nutrition = null, numberOfSteps = null, steps = null, description = null, ingredients = null, numberOfIngredients = null)
        )

        every { recipesRepository.getAllRecipes() } returns recipes
        every { trie.getAllWords() } returns listOf("spaghetti")
        //When
        val result = searchByNameUseCase.searchRecipeByName("aghe")
        //Then
        assertEquals(2, result?.size)
    }

}
