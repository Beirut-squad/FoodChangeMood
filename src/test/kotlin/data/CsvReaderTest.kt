package data

import org.example.data.CsvReader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals


class CsvReaderTest {
    private lateinit var tempFile: File
    private lateinit var csvReader: CsvReader


    @BeforeEach
    fun setup() {
        tempFile = File.createTempFile("test", ".csv")
        tempFile.writeText("name,age\nJohn,30\n\"Jane, A.\",28")
        csvReader = CsvReader(tempFile)
    }

    @Test
    fun `should return list of strings when read a content from file`() {
        // When
        val rows = csvReader.readCsv()

        // Then
        assertEquals(3, rows.size)
        assertEquals("name,age", rows[0])
        assertEquals("John,30", rows[1])
        assertEquals("\"Jane, A.\",28", rows[2])
    }

    @Test
    fun `should correctly split lines with newlines inside quotes`() {
        // Given
        val content = "name,description\n\"item1\",\"line1\nline2\"\nitem2,desc2"
        tempFile.writeText(content)
        csvReader = CsvReader(tempFile)

        // When
        val result = csvReader.readCsv()

        // Then
        assertEquals(3, result.size)
        assertEquals("name,description", result[0])
        assertEquals("\"item1\",\"line1\nline2\"", result[1]) // this one has a newline inside quotes
        assertEquals("item2,desc2", result[2])
    }

    @Test
    fun `should return nothing when file is empty`() {
        // Given
        tempFile.writeText("")

        // When
        val result = csvReader.readCsv()

        // Then
        assertEquals(0, result.size)
    }

}