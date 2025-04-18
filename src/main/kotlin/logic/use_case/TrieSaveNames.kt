package org.example.logic.use_case

class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    var isEndOfWord: Boolean = false
}


class Trie() {
    private val root = TrieNode()

    //insert name in trie
    fun insert(word: String) {
        var current = root
        for (char in word) {
            current = current.children.getOrPut(char) { TrieNode() } // if current not in trie put it
        }
        current.isEndOfWord = true// end the root
    }

    //names starting with special letters
    fun startWith(prefix: String): Boolean {
        var current = root
        for (char in prefix) {
            val node = current.children[char] ?: return false
            current = node
        }
        return true
    }

    // find all the names that start with special letters.
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

    // A recursive function that collects all words under a given node.
    private fun collectWords(node: TrieNode, prefix: String, results: MutableList<String>) {
        if (node.isEndOfWord) results.add(prefix)
        for ((char, child) in node.children) {
            collectWords(child, prefix + char, results)
        }
    }
}