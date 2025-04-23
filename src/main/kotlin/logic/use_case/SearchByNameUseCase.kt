package org.example.logic.use_case

import org.example.error.ThereIsNoNameException
import org.example.logic.RecipesRepository
import org.example.model.Recipe

class SearchByNameUseCase(
    private val recipesRepository: RecipesRepository,
    private val trie: Trie
) {      //to search on name
    fun searchRecipeByName(foodNameToSearch: String): Recipe? {
        val normalizedQuery = foodNameToSearch.trim().lowercase()

        // collecting all the words in the trie
        val allTrieWords = trie.getAllWords()

        // Filter the ones that contain the entered word
        val matchingWords = allTrieWords.filter { it.contains(normalizedQuery) }

        // find words that contain the entered word
        if (matchingWords.isNotEmpty()) {
            val bestMatch = matchingWords.minByOrNull {
                levenshteinDistance(normalizedQuery, it)
            } ?: return null

            // link the name to the recipe
            return recipesRepository.getAllRecipes().firstOrNull {
                it.name?.lowercase()?.contains(bestMatch) == true
            }
        }

        // fallback If there is no match even within the words ,  use Levenshtein for all recipes.
        return recipesRepository.getAllRecipes().minByOrNull { recipe ->
            val name = recipe.name ?:throw ThereIsNoNameException("This recipe has no name.")
            levenshteinDistance(normalizedQuery, name.lowercase())
        }
    }




    private fun levenshteinDistance(foodNameToSearch: String, name: String): Int {
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
            distanceDifference[0][j] = j

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

}

