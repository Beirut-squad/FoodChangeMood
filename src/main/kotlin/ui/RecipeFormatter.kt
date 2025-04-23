package org.example.ui

import org.example.model.Recipe

object RecipeFormatter {

    fun format(recipe: Recipe): String {
        return buildString {
            appendLine("Recipe Name: \n\t${recipe.name?.replace("'", "")}")
            appendLine("Description: \n\t${recipe.description?.replace("\"", "") ?: ""}\n")
            appendLine("Ingredients: ")
            appendLine("${formatIngredients(recipe.ingredients)}\n")
            appendLine("Nutrition: ")
            appendLine(printNutrition(recipe))
            appendLine("Steps: ")
            appendLine(formatSteps(recipe.steps))
            appendLine("============================================================================")
        }
    }

    private fun printNutrition(recipe: Recipe): String {
        return buildString {
            appendLine("\tCalories:  ${recipe.nutrition?.calories}")
            appendLine("\tTotal fat:  ${recipe.nutrition?.totalFat}")
            appendLine("\tSugar:  ${recipe.nutrition?.sugar}")
            appendLine("\tSodium:  ${recipe.nutrition?.sodium}")
            appendLine("\tProtein:  ${recipe.nutrition?.protein}")
            appendLine("\tSaturatedFat:  ${recipe.nutrition?.saturatedFat}")
            appendLine("\tCarbohydrates:  ${recipe.nutrition?.carbohydrates}")
        }
    }
    private fun formatIngredients(items: List<String>?): String {
        return if (items.isNullOrEmpty()) " - No ingredients listed."
        else items.joinToString(separator = "\n") { "\t- ${it.replace("'", "")}" }
    }

    private fun formatSteps(steps: List<String>?): String {
        return if (steps.isNullOrEmpty()) "  1. No steps available."
        else steps.mapIndexed { index, step -> "\t${index + 1}. ${step.replace("'", "")}" }
            .joinToString("\n")
    }
}