package org.example.data

import org.example.logic.RecipesRepository
import org.example.model.Recipe

class RecipesRepositoryCsvImpl(
    private val csvReader: CsvReader,
    private val csvParser: CsvParser
): RecipesRepository {
    private val recipes: MutableList<Recipe> = mutableListOf()

    override fun getAllRecipes(): List<Recipe> {
        return recipes.ifEmpty {
            val lines = csvReader.readCsv().drop(1)
            return csvParser.parseCsvFile(lines).filterNotNull()
        }
    }
}