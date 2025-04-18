package org.example.ui.features_ui

import org.example.logic.use_case.KetoDietUseCase
import org.example.model.Nutrition
import org.example.model.Recipe
import org.example.utils.Colors

class KetoDietUi(
    private val ketoDiet: KetoDietUseCase,
    private val colors: Colors
) {
     fun show(){
        println(colors.cyan("Welcome to Keto Meal Suggester "))
        while (true){
            println(colors.yellow("1. Suggest a Keto Recipe \n2. Go Back "))
            val input: String? = readlnOrNull()
            when(input){
                "1" -> {
                    suggestRecipeForUser()
                    continue
                }
                "2" -> break
                else -> println(colors.red("enter a valid number"))
            }
        }
    }

    private fun suggestRecipeForUser(){
        val recipe = ketoDiet.suggestKetoRecipe()
        println(colors.green("Meal name: ${recipe.name}"))
        println(colors.yellow("Do you want to proceed with Recipe details ? (Y,n) "))
        val input: String? = readlnOrNull()
        if (input == "Y"){
            printRecipeDetails(recipe)
        }
    }

    private fun printRecipeDetails(recipe: Recipe) {
        println(colors.blue("=== Recipe Details ==="))
        printBasicInfo(recipe)
        printTags(recipe.tags)
        printNutritionInfo(recipe.nutrition)
        printSteps(recipe.steps, recipe.numberOfSteps)
        printDescription(recipe.description)
        printIngredients(recipe.ingredients, recipe.numberOfIngredients)
    }

    private fun printBasicInfo(recipe: Recipe) {
        println(colors.green("Name: ${recipe.name ?: "N/A"}"))
        println(colors.green("ID: ${recipe.id ?: "N/A"}"))
        println(colors.green("Preparation Time: ${recipe.minutes ?: "N/A"} minutes"))
        println(colors.green("Contributor ID: ${recipe.contributorId ?: "N/A"}"))
        println(colors.green("Submitted Date: ${recipe.submittedDate ?: "N/A"}\n"))
    }

    private fun printTags(tags: List<String>?) {
        println(colors.green("Tags: ${tags?.joinToString(", ") ?: "None"}\n"))
    }

    private fun printNutritionInfo(nutrition: Nutrition?) {
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

    private fun printSteps(steps: List<String>?, numberOfSteps: Int?) {
        println(colors.green("Number of Steps: ${numberOfSteps ?: "N/A"}"))
        println(colors.blue("--- Steps ---"))
        steps?.forEachIndexed { index, step ->
            println(colors.green("${index + 1}. $step"))
        } ?: println(colors.red("None"))
        println()
    }

    private fun printDescription(description: String?) {
        println(colors.green("Description: ${description ?: "N/A"}\n"))
    }

    private fun printIngredients(ingredients: List<String>?, numberOfIngredients: Int?) {
        println(colors.blue("--- Ingredients ---"))
        println(colors.green("Number of Ingredients: ${numberOfIngredients ?: "N/A"}"))
        ingredients?.forEachIndexed { index, ingredient ->
            println("${index + 1}. $ingredient")
        } ?: println(colors.red("None"))
        println()
    }


}
