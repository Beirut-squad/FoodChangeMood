package org.example.logic.use_case

import org.example.error.ThereIsNoNameException
import org.example.logic.RecipesRepository
import org.example.model.Recipe

class SearchByNameUseCase(
    private val recipesRepository: RecipesRepository,
    private val trie: Trie
) {      //to search on name
    fun searchRecipeByName(foodNameToSearch: String): Recipe? {
        //  Normalize input: remove spaces and lowercase
        val normalizedQuery = foodNameToSearch.trim().lowercase()

        //  Get candidate names from Trie that start with the same 2 letters
        val candidateNames = trie.getWordsWithPrefix(normalizedQuery.take(2)).take(100)
            // Limit to 100 results for performance

        //  Find best match using Levenshtein distance
        val bestMatch = candidateNames.minByOrNull { candidate ->
            levenshteinDistance(normalizedQuery, candidate.lowercase())
        }

        //  Convert recipe list into map for quick lookup
        val recipesByName = recipesRepository.getAllRecipes()
            .associateBy { //دي ذي الماب بخزن فيها الوصفة بس
                it.name?.lowercase() ?: throw ThereIsNoNameException("Recipe has no name.")
            }


        // 5 Return the recipe matching best name (if found)
        return bestMatch?.lowercase()?.let { recipesByName[it] }

    }
}


    fun levenshteinDistance(foodNameToSearch: String, name: String): Int {
        when {
            foodNameToSearch == name -> return 0
            foodNameToSearch.isEmpty() -> return name.length
            name.isEmpty() -> return foodNameToSearch.length
        }
        val lengthFoodNameToSearch = foodNameToSearch.length
        val lengthName = name.length
        val distanceDifference = Array(lengthFoodNameToSearch + 1) { Array(lengthName + 1) { 0 } }

        for (i in 0..lengthFoodNameToSearch)  // First column
            distanceDifference[i][0] = i

        for (j in 0..lengthName)                // First row
            distanceDifference[j][0] = j

        for (i in 1..lengthFoodNameToSearch) {
            for (j in 1..lengthName) {
                val costSubstitute = if (foodNameToSearch[i - 1] == name[j - 1]) 0 else 1
                distanceDifference[i][j] = minOf(
                    distanceDifference[i - 1][j] + 1, // Deletion
                    distanceDifference[i][j - 1] + 1,  // Insertion
                    distanceDifference[i - 1][j - 1] + costSubstitute  // Substitution
                )
            }
        }
        return distanceDifference[lengthFoodNameToSearch][lengthName]

    }

