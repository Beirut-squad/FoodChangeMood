package data

import io.mockk.every
import io.mockk.mockk
import org.example.data.CsvReader
import org.example.data.FileGetter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.error.InstanceCreationException
import java.io.File
import kotlin.test.assertEquals


class CsvReaderTest {
    private val tempFile: File = mockk(relaxed = true)
    private val fileGetter: FileGetter = mockk(relaxed = true)
    private lateinit var csvReader: CsvReader


    @BeforeEach
    fun setup() {
        csvReader = CsvReader(tempFile, fileGetter)
    }

    @Test
    fun `should return list of strings when read a content from file`() {
        // Given
        every { fileGetter.getFile(any()) } returns "name,age\nJohn,30\n\"Jane, A.\",28"

        // When
        val rows = csvReader.readCsv()

        // Then
        assertEquals(3, rows.size)
        assertEquals("name,age", rows[0])
        assertEquals("John,30", rows[1])
        assertEquals("\"Jane, A.\",28", rows[2])
    }

    @Test
    fun `should return exception when file does not exist`() {
        // Given
        every { fileGetter.getFile(any()) } throws InstanceCreationException("", Exception())

        // When && Then
        assertThrows<InstanceCreationException> {
            csvReader.readCsv()
        }

    }

    @Test
    fun `should correctly split lines with newlines inside quotes`() {
        // Given
        every { fileGetter.getFile(any()) } returns "name,description\n\"item1\",\"line1\nline2\"\nitem2,desc2"

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
        every { fileGetter.getFile(any()) } returns ""

        // When
        val result = csvReader.readCsv()

        // Then
        assertEquals(0, result.size)
    }

}