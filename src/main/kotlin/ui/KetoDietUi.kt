package org.example.ui

import org.example.logic.KetoDiet
import org.example.model.Nutrition
import org.example.model.Recipe

class KetoDietUi(
    private val ketoDiet: KetoDiet,
) {
     fun show(){
        println("Welcome to Keto Meal Suggester ")
        while (true){
            println("1. Suggest a Keto Recipe \n2. Go Back ")
            val input: String? = readlnOrNull()
            when(input){
                "1" -> {
                    suggestRecipeForUser()
                    continue
                }
                "2" -> break
                else -> println("enter a valid number")
            }
        }
    }

    private fun suggestRecipeForUser(){
        val recipe = ketoDiet.suggestKetoRecipe()
        println("Meal name: ${recipe.name}")
        println("Do you want to proceed with Recipe details ? (Y,n) ")
        val input: String? = readlnOrNull()
        if (input == "Y"){
            printRecipeDetails(recipe)
        }
    }

    private fun printRecipeDetails(recipe: Recipe) {
        println("=== Recipe Details ===")
        printBasicInfo(recipe)
        printTags(recipe.tags)
        printNutritionInfo(recipe.nutrition)
        printSteps(recipe.steps, recipe.numberOfSteps)
        printDescription(recipe.description)
        printIngredients(recipe.ingredients, recipe.numberOfIngredients)
    }

    private fun printBasicInfo(recipe: Recipe) {
        println("Name: ${recipe.name ?: "N/A"}")
        println("ID: ${recipe.id ?: "N/A"}")
        println("Preparation Time: ${recipe.minutes ?: "N/A"} minutes")
        println("Contributor ID: ${recipe.contributorId ?: "N/A"}")
        println("Submitted Date: ${recipe.submittedDate ?: "N/A"}\n")
    }

    private fun printTags(tags: List<String>?) {
        println("Tags: ${tags?.joinToString(", ") ?: "None"}\n")
    }

    private fun printNutritionInfo(nutrition: Nutrition?) {
        println("--- Nutrition Information ---")
        nutrition?.let {
            println("Calories: ${it.calories ?: "N/A"}")
            println("Total Fat: ${it.totalFat ?: "N/A"} g")
            println("Sugar: ${it.sugar ?: "N/A"} g")
            println("Sodium: ${it.sodium ?: "N/A"} mg")
            println("Protein: ${it.protein ?: "N/A"} g")
            println("Saturated Fat: ${it.saturatedFat ?: "N/A"} g")
            println("Carbohydrates: ${it.carbohydrates ?: "N/A"} g")
        } ?: println("Nutrition Info: N/A\n")
    }

    private fun printSteps(steps: List<String>?, numberOfSteps: Int?) {
        println("Number of Steps: ${numberOfSteps ?: "N/A"}")
        println("--- Steps ---")
        steps?.forEachIndexed { index, step ->
            println("${index + 1}. $step")
        } ?: println("None")
        println()
    }

    private fun printDescription(description: String?) {
        println("Description: ${description ?: "N/A"}\n")
    }

    private fun printIngredients(ingredients: List<String>?, numberOfIngredients: Int?) {
        println("--- Ingredients ---")
        println("Number of Ingredients: ${numberOfIngredients ?: "N/A"}")
        ingredients?.forEachIndexed { index, ingredient ->
            println("${index + 1}. $ingredient")
        } ?: println("None")
        println()
    }


}
