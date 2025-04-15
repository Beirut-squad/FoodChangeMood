package org.example.logic

import org.example.model.Nutrition
import org.example.model.Recipe
import org.example.model.getKetoScore

class KetoDiet(private val recipesRepository: RecipesRepository) {

    private var i = 0
    private val recipes = recipesRepository.getAllRecipes()

    fun suggestKetoRecipe(): Recipe{
        val ketoRecipes = sortKetoRecipes()
        return ketoRecipes[i++ % ketoRecipes.size]
    }

    fun sortKetoRecipes(): List<Recipe>{
        return getAllKetoRecipes().sortedByDescending { ketoRecipe ->
            ketoRecipe.nutrition?.getKetoScore()
        }
    }


    private fun isValidKetoRecipe(
        totalFats: Float?,
        saturatedFats: Float?,
        sugar: Float?,
        carbohydrates: Float?
    ): Boolean {
        if (totalFats == null || saturatedFats == null || sugar == null || carbohydrates == null)
            return false

        val ketoScore = (totalFats + saturatedFats) - (sugar + carbohydrates)
        return (totalFats in 10.0f..90.0f &&
                saturatedFats in 3.0f..30.0f &&
                sugar < 5.0f &&
                carbohydrates < 10.0f &&
                ketoScore >= 5.0f)
    }

    private fun getAllKetoRecipes(): List<Recipe> {
        return recipes.filter { recipe ->
            isValidKetoRecipe(
                recipe.nutrition?.totalFat,
                recipe.nutrition?.saturatedFat,
                recipe.nutrition?.sugar,
                recipe.nutrition?.carbohydrates
            )
        }
    }

}