package org.example.SearchByNameAlgo

object LevenshteinDistance {

    /**
     * Calculates the Levenshtein distance between two strings.
     * This distance measures the minimum number of single-character edits
     * (insertions, deletions, or substitutions) required to change one word into the other.
     *
     * @param name The first string (e.g., user input).
     * @param foodNameToSearch The second string (e.g., food name in database).
     * @return The Levenshtein distance as an integer.
     */
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