package org.example.logic.use_case

import org.example.SearchByNameAlgo.LevenshteinDistance
import org.example.SearchByNameAlgo.KMP
import org.example.logic.RecipesRepository
import org.example.model.Recipe
import org.example.utils.SearchByNameAlgo.Trie

class SearchByNameUseCase(
    private val recipesRepository: RecipesRepository,
    private val trie: Trie
) {
    fun searchRecipeByName(foodNameToSearch: String): List<Recipe>? {
        val normalizedQuery = foodNameToSearch.trim().lowercase()
        if (normalizedQuery.isBlank()) {
            return emptyList()
        }

        // Get all words from Trie
        val allTrieWords = trie.getAllWords()
        // Use KMP to filter matching words

        val matchingWords = allTrieWords.filter { word ->
            KMP.contains(word, normalizedQuery) // Ensure KMP matches correctly
        }
        // If we found matches, pick the closest one with Levenshtein
        if (matchingWords.isNotEmpty()) {
            val bestMatch = matchingWords.minByOrNull {
                LevenshteinDistance.levenshteinDistance(normalizedQuery, it)
            } ?: return null

            return recipesRepository.getAllRecipes()
                .filter { it.name?.lowercase()?.contains(bestMatch) == true } // Ensure exact match filtering
                .take(3) // Top 3 matches
        }

        // Fallback to comparing against all recipes if no trie match
        return recipesRepository.getAllRecipes()
            .map { recipe ->
                val name = recipe.name ?: throw IllegalArgumentException("Recipe name cannot be null.")
                val distance = LevenshteinDistance.levenshteinDistance(normalizedQuery, name.lowercase())
                recipe to distance
            }
            .sortedBy { it.second } // Sort by Levenshtein distance
            .take(3) // Top 3 closest matches
            .map { it.first } // Return only the recipes
    }

}

