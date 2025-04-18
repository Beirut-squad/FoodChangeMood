package org.example.data

import java.io.File

class CsvReader(private val csvInputFile: File) {

    fun readCsv(): List<String> {
        val content = csvInputFile.readText()
        return splitIntoRows(content)
    }

    private fun splitIntoRows(content: String): List<String> {
        val rows = mutableListOf<String>()
        val currentRow = StringBuilder()
        var inQuotes = false

        for (char in content) {
            when {
                char == '"' -> {
                    inQuotes = !inQuotes
                    currentRow.append(char)
                }

                char == '\n' && !inQuotes -> {
                    // True end of row
                    rows.add(currentRow.toString())
                    currentRow.clear()
                }

                else -> {
                    currentRow.append(char)
                }
            }
        }

        // Add the last row if it's not empty
        if (currentRow.isNotEmpty()) {
            rows.add(currentRow.toString())
        }

        return rows
    }

}