package org.example.data

import org.example.model.Recipe
import org.example.model.toNutrition
import org.example.stringToDate

class CsvParser {

    fun parseCsvFile(csvFileList: List<String>): List<Recipe?> {
        return csvFileList.map {
            parseCsvToRecipe(splitToStrings(it))
        }
    }

    private fun parseCsvToRecipe(csvLine: List<String>): Recipe? {
        return csvLine.getOrNull(RecipesColumnIndex.NUTRITION)?.let { splitList(it).toNutrition() }?.let {
            Recipe(
                name = csvLine.getOrNull(RecipesColumnIndex.NAME),
                id = csvLine.getOrNull(RecipesColumnIndex.ID),
                minutes = csvLine.getOrNull(RecipesColumnIndex.MINUTES)?.toIntOrNull(),
                contributorId = csvLine.getOrNull(RecipesColumnIndex.CONTRIBUTOR_ID),
                submittedDate = csvLine.getOrNull(RecipesColumnIndex.DATE)?.let { stringToDate(it) },
                tags = csvLine.getOrNull(RecipesColumnIndex.TAGS)?.let { splitList(it) },
                nutrition = it,
                numberOfSteps = csvLine.getOrNull(RecipesColumnIndex.NUMBER_OF_STEPS)?.toIntOrNull(),
                steps = csvLine.getOrNull(RecipesColumnIndex.STEPS)?.let { splitList(it) } ?: emptyList(),
                description = csvLine.getOrNull(RecipesColumnIndex.DESCRIPTION),
                ingredients = csvLine.getOrNull(RecipesColumnIndex.INGREDIENTS)?.let { splitList(it) }
                    ?: emptyList(),
                numberOfIngredients = csvLine.getOrNull(RecipesColumnIndex.NUMBER_OF_INGREDIENTS)?.toIntOrNull()
            )
        }
    }

    private fun splitToStrings(line: String): List<String> {
        var isInsideDoubleQuotation = false
        val currentLine = mutableListOf<String>()
        var currentToken = ""

        line.forEach { character ->
            when (character) {
                '"' -> { isInsideDoubleQuotation = !isInsideDoubleQuotation }
                ',' -> {
                    if (isInsideDoubleQuotation) {
                        currentToken += ','
                    } else {
                        currentLine.add(currentToken)
                        currentToken = ""
                    }
                }
                else -> { currentToken += character }
            }
        }
        currentLine.add(currentToken)
        return currentLine.filter { it.isNotEmpty() }
    }

    private fun splitList(listAsString: String): List<String> {
        return listAsString.drop(1).dropLast(1).split(',').map { it.trim() }
    }
}