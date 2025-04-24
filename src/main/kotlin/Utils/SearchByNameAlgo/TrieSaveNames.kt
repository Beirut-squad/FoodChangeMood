package org.example.SearchByNameAlgo

// Represents a node in the Trie structure
class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    var isEndOfWord: Boolean = false
}

class Trie {

    private val root = TrieNode()

    /**
     * Inserts a word into the Trie.
     * Time Complexity: O(n), where n is the length of the word
     */
    fun insert(word: String) {
        var current = root
        for (char in word) {
            current = current.children.getOrPut(char) { TrieNode() }
        }
        current.isEndOfWord = true
    }

    /**
     * Checks if any word in the Trie starts with the given prefix.
     * Useful for autocomplete suggestions.
     * Time Complexity: O(p), where p is the prefix length
     */
    fun startsWith(prefix: String): Boolean {
        var current = root
        for (char in prefix) {
            current = current.children[char] ?: return false
            current = current
        }
        return true
    }

    /**
     * Returns all words in the Trie that start with the given prefix.
     * Time Complexity: O(p + m), p = prefix length, m = number of words starting with prefix
     */
    fun getWordsWithPrefix(prefix: String): List<String> {
        val results = mutableListOf<String>()
        var current = root
        for (char in prefix) {
            val node = current.children[char] ?: return emptyList()
            current = node
        }
        collectWords(current, prefix, results)
        return results
    }

    /**
     * Returns all words stored in the Trie.
     * Time Complexity: O(n), where n is the total characters across all stored words
     */
    fun getAllWords(): List<String> {
        val results = mutableListOf<String>()
        collectWords(root, "", results)
        return results
    }

    /**
     * Helper method to collect words from the given Trie node.
     */
    private fun collectWords(node: TrieNode, currentPrefix: String, results: MutableList<String>) {
        if (node.isEndOfWord) results.add(currentPrefix)
        for ((char, child) in node.children) {
            collectWords(child, currentPrefix + char, results)
        }
    }
}
