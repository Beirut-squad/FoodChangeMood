package org.example.logic

import org.example.data.RecipesRepositoryCsvImpl
import org.example.model.Recipe
import kotlin.random.Random

class GetTenRandomRecipesIncludePotato( private val recipesRepoImp:RecipesRepositoryCsvImpl) {
    fun findPotatoMeals(): List<Recipe> {
        val allRecipes = recipesRepoImp.getAllRecipes()
        //17679
        return allRecipes.filter { recipe ->
            recipe.ingredients?.any { ingredient ->
                ingredient.contains("potato", ignoreCase = true)
            } == true
        }.shuffled(Random.Default).take(POTATO.NUMBER_OF_RECIPES)
    }
}
object POTATO {
    const val NUMBER_OF_RECIPES = 0
}

