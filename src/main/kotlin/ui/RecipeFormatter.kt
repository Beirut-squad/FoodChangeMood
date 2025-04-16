package org.example.ui

import org.example.model.Recipe

object RecipeFormatter {

    fun format(recipe: Recipe): String {
        return buildString {
            appendLine("Recipe Name: ${recipe.name}")
            appendLine("\tDescription: ${recipe.description ?: ""}")
            appendLine("\tIngredients: ")
            appendLine(formatList(recipe.ingredients))
            appendLine("\tSteps: ")
            appendLine(formatSteps(recipe.steps))
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