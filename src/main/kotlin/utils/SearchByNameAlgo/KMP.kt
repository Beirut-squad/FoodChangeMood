package org.example.SearchByNameAlgo

object KMP {

    /**
     * Builds the prefix table (also known as LPS - Longest Prefix Suffix).
     * This table helps the KMP algorithm avoid unnecessary comparisons.
     *
     * @param pattern The search pattern.
     * @return IntArray representing the prefix table.
     */
    private fun buildPrefixTable(pattern: String): IntArray {
        val prefixTable = IntArray(pattern.length)
        var matchLength = 0 // length of the previous longest prefix suffix

        for (i in 1 until pattern.length) {
            while (matchLength > 0 && pattern[i] != pattern[matchLength]) {
                matchLength = prefixTable[matchLength - 1]
            }

            if (pattern[i] == pattern[matchLength]) {
                matchLength++
                prefixTable[i] = matchLength
            }
        }

        return prefixTable
    }

    /**
     * Checks if the given pattern exists in the text using the KMP algorithm.
     *
     * @param text The text to search within.
     * @param pattern The pattern to search for.
     * @return true if pattern exists in text, otherwise false.
     */
    fun contains(text: String, pattern: String): Boolean {
        if (pattern.isEmpty()) return true      // Empty pattern matches anything
        if (text.isEmpty()) return false        // Can't match anything in an empty text

        val prefixTable = buildPrefixTable(pattern)
        var patternIndex = 0 // Index for pattern traversal

        for (textIndex in text.indices) {
            // Slide pattern back if mismatch
            while (patternIndex > 0 && text[textIndex] != pattern[patternIndex]) {
                patternIndex = prefixTable[patternIndex - 1]
            }

            // If match, move forward
            if (text[textIndex] == pattern[patternIndex]) {
                patternIndex++
                if (patternIndex == pattern.length) return true // Found full match
            }
        }

        return false // No match found
    }
}
