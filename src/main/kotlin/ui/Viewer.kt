package org.example.ui

import org.example.model.Nutrition
import org.example.model.Recipe
import org.example.utils.Colors

class Viewer(
    private val colors: Colors
) {

    fun printTitle(
        text: String,
        withNewLine: Boolean = true
    ) {
        val coloredText = colors.cyan(text)
        if (withNewLine) {
            println(coloredText)
        } else {
            print(coloredText)
        }
    }

    fun printError(
        text: String,
        withNewLine: Boolean = true
    ) {
        val coloredText = colors.red(text)
        if (withNewLine) {
            println(coloredText)
        } else {
            print(coloredText)
        }
    }

    fun printCorrectOutput(
        text: String,
        withNewLine: Boolean = true
    ) {
        val coloredText = colors.green(text)
        if (withNewLine) {
            println(coloredText)
        } else {
            print(coloredText)
        }
    }

    fun printLoader(
        text: String,
        withNewLine: Boolean = true
    ) {
        val coloredText = colors.blue(text)
        if (withNewLine) {
            println(coloredText)
        } else {
            print(coloredText)
        }
    }

    fun printOption(
        text: String,
        withNewLine: Boolean = true
    ) {
        val coloredText = colors.purple(text)
        if (withNewLine) {
            println(coloredText)
        } else {
            print(coloredText)
        }
    }

    fun printInfoLine(
        text: String,
        withNewLine: Boolean = true
    ) {
        val coloredText = colors.yellow(text)
        if (withNewLine) {
            println(coloredText)
        } else {
            print(coloredText)
        }
    }

    fun printPlainText(
        text: String,
        withNewLine: Boolean = true
    ) {
        if (withNewLine) {
            println(text)
        } else {
            print(text)
        }
    }


    fun printRecipeDetails(recipe: Recipe) {
        println(colors.blue("=== Recipe Details ==="))
        printBasicInfo(recipe)
        printTags(recipe.tags)
        printNutritionInfo(recipe.nutrition)
        printSteps(recipe.steps, recipe.numberOfSteps)
        printDescription(recipe.description)
        printIngredients(recipe.ingredients, recipe.numberOfIngredients)
    }

    fun printBasicInfo(recipe: Recipe) {
        println(colors.green("Name: ${recipe.name ?: "N/A"}"))
        println(colors.green("ID: ${recipe.id ?: "N/A"}"))
        println(colors.green("Preparation Time: ${recipe.minutes ?: "N/A"} minutes"))
        println(colors.green("Contributor ID: ${recipe.contributorId ?: "N/A"}"))
        println(colors.green("Submitted Date: ${recipe.submittedDate ?: "N/A"}\n"))
    }

    fun printTags(tags: List<String>?) {
        println(colors.green("Tags: ${tags?.joinToString(", ") ?: "None"}\n"))
    }

    fun printNutritionInfo(nutrition: Nutrition?) {
        println(colors.green("--- Nutrition Information ---"))
        nutrition?.let {
            println(colors.green("Calories: ${it.calories ?: "N/A"}"))
            println(colors.green("Total Fat: ${it.totalFat ?: "N/A"} g"))
            println(colors.green("Sugar: ${it.sugar ?: "N/A"} g"))
            println(colors.green("Sodium: ${it.sodium ?: "N/A"} mg"))
            println(colors.green("Protein: ${it.protein ?: "N/A"} g"))
            println(colors.green("Saturated Fat: ${it.saturatedFat ?: "N/A"} g"))
            println(colors.green("Carbohydrates: ${it.carbohydrates ?: "N/A"} g"))
        } ?: println(colors.red("Nutrition Info: N/A\n"))
    }

    fun printSteps(steps: List<String>?, numberOfSteps: Int?) {
        println(colors.green("Number of Steps: ${numberOfSteps ?: "N/A"}"))
        println(colors.blue("--- Steps ---"))
        steps?.forEachIndexed { index, step ->
            println(colors.green("${index + 1}. $step"))
        } ?: println(colors.red("None"))
        println()
    }

    fun printDescription(description: String?) {
        println(colors.green("Description: ${description ?: "N/A"}\n"))
    }

    fun printIngredients(ingredients: List<String>?, numberOfIngredients: Int?) {
        println(colors.blue("--- Ingredients ---"))
        println(colors.green("Number of Ingredients: ${numberOfIngredients ?: "N/A"}"))
        ingredients?.forEachIndexed { index, ingredient ->
            println("${index + 1}. $ingredient")
        } ?: println(colors.red("None"))
        println()
    }

}