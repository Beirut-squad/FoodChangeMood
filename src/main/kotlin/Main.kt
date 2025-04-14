package org.example

import org.example.data.CsvParser
import org.example.data.CsvReader
import org.example.data.FileNames.FOOD_CSV_FILE
import org.example.data.FileNames.PROCESSED_CSV_FILE
import java.io.File

fun main() {
    val inputCsvFile = File(FOOD_CSV_FILE)
    val processedCsvFile = File(PROCESSED_CSV_FILE)

    val csvReader = CsvReader(
        inputCsvFile,
        processedCsvFile
    )

    val csvFile = csvReader.readCsv()

    val csvParser = CsvParser()

    val tmp = File("output.csv")
    tmp.createNewFile()

    val recipes = csvParser.parseCsvFile(csvFile)

    for (recipe in recipes) {
        tmp.appendText(recipe?.toString() ?: "")
        tmp.appendText("\n\n\n")
    }
}
