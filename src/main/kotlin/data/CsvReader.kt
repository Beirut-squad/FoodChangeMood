package org.example.data

import java.io.File

class CsvReader(
    private val csvInputFile: File,
    private val csvProcessedFile: File
) {

    fun readCsv(): List<String> {
        removeExtraNewLines()
        return readProcessedCsv()
    }

    private fun removeExtraNewLines() {
        val csvInputBufferedReader = csvInputFile.bufferedReader()
        val csvProcessedBufferedWriter = csvProcessedFile.bufferedWriter()

        val builder = StringBuilder()
        var quoteCount = 0

        csvInputBufferedReader.forEachLine { line ->
            builder.append(line).append(" ")

            quoteCount += line.count { it == '"' }

            if (quoteCount % 2 == 0) {
                csvProcessedBufferedWriter.write(builder.toString().trim())
                csvProcessedBufferedWriter.newLine()
                builder.clear()
                quoteCount = 0
            }
        }
    }

    private fun readProcessedCsv(): List<String> {
        if (csvProcessedFile.exists()) {
            return csvProcessedFile.readLines()
        } else {
            throw Exception("Couldn't find the csv file.")
        }
    }
}