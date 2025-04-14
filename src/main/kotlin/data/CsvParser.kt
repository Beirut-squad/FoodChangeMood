package org.example.data

import org.example.model.Recipe
import org.example.model.toNutrition

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
                minutes = csvLine.getOrNull(RecipesColumnIndex.MINUTES),
                contributorId = csvLine.getOrNull(RecipesColumnIndex.CONTRIBUTOR_ID),
                submittedDate = csvLine.getOrNull(RecipesColumnIndex.DATE),
                tags = csvLine.getOrNull(RecipesColumnIndex.TAGS)?.let { splitList(it) },
                nutrition = it,
                numberOfSteps = csvLine.getOrNull(RecipesColumnIndex.NUMBER_OF_STEPS),
                steps = splitList(csvLine.getOrNull(RecipesColumnIndex.STEPS)!!),
                description = csvLine.getOrNull(RecipesColumnIndex.DESCRIPTION),
                ingredients = splitList(csvLine.getOrNull(RecipesColumnIndex.INGREDIENTS)!!),
                numberOfIngredients = csvLine.getOrNull(RecipesColumnIndex.NUMBER_OF_INGREDIENTS)
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
        val removedBracesList = listAsString.drop(1).dropLast(1)
        val result = removedBracesList.split(',')
        return result
    }
}