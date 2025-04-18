package org.example.data


import utils.toDate
import org.example.model.Recipe
import org.example.model.toNutrition

class CsvParser {

    fun parseCsvFile(csvFileList: List<String>): List<Recipe?> {
        return csvFileList.map {
            parseCsvToRecipe(splitToStrings(it))
        }
    }

    private fun parseCsvToRecipe(csvLine: List<String>): Recipe? {
        return Recipe(
            name = csvLine.getOrNull(RecipesColumnIndex.NAME),
            id = csvLine.getOrNull(RecipesColumnIndex.ID),
            minutes = csvLine.getOrNull(RecipesColumnIndex.MINUTES)?.toIntOrNull(),
            contributorId = csvLine.getOrNull(RecipesColumnIndex.CONTRIBUTOR_ID),
            submittedDate = csvLine.getOrNull(RecipesColumnIndex.DATE)?.let { it.toDate() },
            tags = csvLine.getOrNull(RecipesColumnIndex.TAGS)?.let { splitList(it) },
            nutrition = csvLine.getOrNull(RecipesColumnIndex.NUTRITION)?.let { splitList(it).toNutrition() },
            numberOfSteps = csvLine.getOrNull(RecipesColumnIndex.NUMBER_OF_STEPS)?.toIntOrNull(),
            steps = csvLine.getOrNull(RecipesColumnIndex.STEPS)?.let { splitList(it) } ?: emptyList(),
            description = csvLine.getOrNull(RecipesColumnIndex.DESCRIPTION),
            ingredients = csvLine.getOrNull(RecipesColumnIndex.INGREDIENTS)?.let { splitList(it) } ?: emptyList(),
            numberOfIngredients = csvLine.getOrNull(RecipesColumnIndex.NUMBER_OF_INGREDIENTS)
                ?.let { it.substring(0, it.length - 1) }?.toIntOrNull()
        )
    }

    private fun splitToStrings(line: String): List<String> {
        val result = mutableListOf<String>()
        val currentToken = StringBuilder()
        var insideQuotes = false
        for (char in line) {
            when {
                char == '"' -> {
                    insideQuotes = !insideQuotes  // Toggle quote state
                    currentToken.append(char)     // Keep quotes in the output
                }

                char == ',' && !insideQuotes -> {
                    result.add(currentToken.toString())
                    currentToken.clear()
                }

                else -> {
                    currentToken.append(char)
                }
            }
        }
        // Add the last token
        result.add(currentToken.toString())
        return result
    }

    private fun splitList(listAsString: String): List<String> {
        return listAsString.takeIf { it.startsWith("\"[") && it.endsWith("]\"") }
            ?.let { it.substring(2, it.length - 2) }?.split(",")?.map { it.trim() } ?: emptyList()
    }
}