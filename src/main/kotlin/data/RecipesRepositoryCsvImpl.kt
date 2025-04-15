package org.example.data

import org.example.logic.RecipesRepository
import org.example.model.Recipe

class RecipesRepositoryCsvImpl(
    private val csvReader: CsvReader,
    private val csvParser: CsvParser
): RecipesRepository {
    override fun getAllRecipes(): List<Recipe> {
        val lines = csvReader.readCsv().drop(1)
        return csvParser.parseCsvFile(lines).filterNotNull()
    }
}