package org.example.ui

import org.example.model.Recipe

object RecipeFormatter {

    fun format(recipe: Recipe): String {
        return buildString {
            appendLine("Recipe Name: \n\t${recipe.name?.replace("'", "")}")
            appendLine("Description: \n\t${recipe.description?.replace("\"", "") ?: ""}")
            appendLine("Ingredients: ")
            appendLine(formatList(recipe.ingredients))
            appendLine("Nutrition: ")
            appendLine(printNutrition(recipe))
            appendLine("Steps: ")
            appendLine(formatSteps(recipe.steps))
            appendLine("============================================================================")
        }
    }

    private fun printNutrition(recipe: Recipe): String {
        return buildString {
            appendLine("Calories  :  \n${recipe.nutrition!!.calories}")
            appendLine("Total fat :   \n${recipe.nutrition.totalFat}")
            appendLine("Sugar :  \n${recipe.nutrition.sugar}")
            appendLine("Sodium :  \n${recipe.nutrition.sodium}")
            appendLine("Protein :  \n${recipe.nutrition.protein}")
            appendLine("SaturatedFat :  \n${recipe.nutrition.saturatedFat}")
            appendLine("Carbohydrates :  \n${recipe.nutrition.carbohydrates}")
            appendLine("============================================================================")
        }
    }

    fun idAndNameAndDate(recipe: Recipe): String{
        return buildString {
            appendLine("Recipe ID: \n\t${recipe.id}")
            appendLine("Recipe Name: \n\t${recipe.name?.replace("'", "")}")
            appendLine("Recipe Date: \n\t${recipe.submittedDate}")
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