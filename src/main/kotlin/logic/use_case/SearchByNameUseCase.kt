package org.example.logic.use_case

import org.example.error.ThereIsNoNameException
import org.example.logic.RecipesRepository
import org.example.model.Recipe

class SearchByNameUseCase(
    private val recipesRepository: RecipesRepository,
    private val trie: Trie
) {

    init {
        // ملء الـ Trie بالأسماء عند إنشاء الـ UseCase
        recipesRepository.getAllRecipes().forEach {
            it.name?.let { name -> trie.insert(name.lowercase().trim()) }
        }
    }

    //to search on name
    fun searchRecipeByName(foodNameToSearch: String): Recipe? {
        //  Normalize input: remove spaces and lowercase
        val normalizedQuery = foodNameToSearch.trim().lowercase()

        //  Get candidate names from Trie that start with the same 2 letters
        val candidateNames = trie.getWordsWithPrefix("").take(1000) // 2

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

    private fun levenshteinDistance(foodNameToSearch: String, name: String): Int {
        if (foodNameToSearch == name) return 0
        if (foodNameToSearch.isEmpty()) return name.length
        if (name.isEmpty()) return foodNameToSearch.length

        val len1 = foodNameToSearch.length
        val len2 = name.length

        // Create an array to calculate the distance
        val distanceDifference = Array(len1 + 1) { IntArray(len2 + 1) }

        // تهيئة أول صف وأول عمود
        for (i in 0..len1) {
            distanceDifference[i][0] = i
        }
        for (j in 0..len2) {
            distanceDifference[0][j] = j
        }

        // Calculating distance using Levenshtein's rule
        for (i in 1..len1) {
            for (j in 1..len2) {
                val cost = if (foodNameToSearch[i - 1] == name[j - 1]) 0 else 1
                distanceDifference[i][j] = minOf(
                    distanceDifference[i - 1][j] + 1, // delete
                    distanceDifference[i][j - 1] + 1, // add
                    distanceDifference[i - 1][j - 1] + cost // replacing
                )
            }
        }

        return distanceDifference[len1][len2] // Return the calculated distance
    }

}

