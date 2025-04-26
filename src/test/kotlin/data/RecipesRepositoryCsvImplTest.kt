package data

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.data.CsvParser
import org.example.data.CsvReader
import org.example.data.RecipesRepositoryCsvImpl
import org.example.logic.RecipesRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RecipesRepositoryCsvImplTest {
    private val csvReader: CsvReader = mockk(relaxed = true)
    private val csvParser: CsvParser = mockk(relaxed = true)

    private lateinit var recipesRepository: RecipesRepository

    @BeforeEach
    fun setup() {
        recipesRepository = RecipesRepositoryCsvImpl(csvReader, csvParser)
    }

    @Test
    fun `should call readCsv function from csv reader when calling getAllRecipes`() {
        // When
        recipesRepository.getAllRecipes()

        // Then
        verify { csvReader.readCsv() }
    }

    @Test
    fun `should call parseCsvFile function from csv parser when calling getAllRecipes`() {
     // When
     recipesRepository.getAllRecipes()

     // Then
     verify { csvParser.parseCsvFile(any()) }
    }

    @Test
    fun `should drop first row from readCsv function in csv reader when calling getAllRecipes`() {
        // Given
        val lines = listOf(
            "String 1",
            "String 2"
        )
        every { csvReader.readCsv() } returns lines

        // When
        recipesRepository.getAllRecipes()

        // Then
        verify { csvParser.parseCsvFile(lines.drop(1)) }
    }

    @Test
    fun `should filter null values from parseCsvFile function in csv parser when calling getAllRecipes`() {
        // Given
        val recipes = listOf(
            null,
            makeRecipeForRecipeRepositoryHelper(),
            makeRecipeForRecipeRepositoryHelper(),
            null
        )
        every { csvParser.parseCsvFile(any()) } returns recipes

        // When
        val result = recipesRepository.getAllRecipes()

        // Then
        assertThat(result).containsNoneOf(null, null)
    }
}