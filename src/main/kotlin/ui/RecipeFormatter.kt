package org.example.ui

import org.example.model.Recipe

object RecipeFormatter {

    fun format(recipe: Recipe): String {
        return buildString {
            appendLine("Recipe Name: \n\t${recipe.name?.replace("'", "")}")
            appendLine("Description: \n\t${recipe.description?.replace("\"", "") ?: ""}")
            appendLine("Ingredients: ")
            appendLine(formatList(recipe.ingredients))
            appendLine("Steps: ")
            appendLine(formatSteps(recipe.steps))

            appendLine("============================================================================")
        }
    }

    private fun formatList(items: List<String>?): String {
        return if (items.isNullOrEmpty()) " - No ingredients listed."
        else items.joinToString(separator = "\n") { "\t\t- ${it.replace("'", "")}" }
    }

    private fun formatSteps(steps: List<String>?): String {
        return if (steps.isNullOrEmpty()) "  1. No steps available."
        else steps.mapIndexed { index, step -> "\t\t${index + 1}. ${step.replace("'", "")}" }
            .joinToString("\n")
    }
}