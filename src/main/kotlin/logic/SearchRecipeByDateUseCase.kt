package org.example.logic

import org.example.error.NoRecipesFoundForTheGivenDateException
import org.example.error.RecipeNotFoundException
import org.example.model.Recipe
import Utils.stringToDate


class SearchRecipeByDateUseCase (
    private val recipesRepository: RecipesRepository
){

    /*   Search Foods by Add Date: Use Kotlin’s Date class to represent the date in the meal entity. (Done)
         Let the user input a date and return a list of IDs and names of meals added on that date.  (Done)
         The user should be able to view details of a specific meal by entering its ID. (Done)
          Handle exceptions for: (In UI)
        - Incorrect date format.
        - No meals were found for the given date. Ensure different exceptions are used for both cases. */

    fun searchRecipeByDate(enteredDate:String): List<Pair<String, String>>{
        return recipesRepository.getAllRecipes()
            .filter(::checkNoNullValue)
            .filter { it.submittedDate == stringToDate(enteredDate) }
            .takeIf { it.isNotEmpty() }
            ?.map {recipe->
                (recipe.id)!! to recipe.name!!
            } ?: throw NoRecipesFoundForTheGivenDateException("No meals were found for the given date")
    }

    fun viewDetailsOfRecipeByID(recipeId: String): Recipe?{
        return recipesRepository.getAllRecipes()
            .filter { it.id == recipeId }
            ?.let { it[0] } ?: throw RecipeNotFoundException("Recipe Not Found")
    }


    private fun checkNoNullValue(recipe: Recipe): Boolean{
        return recipe.name != null && recipe.id != null
    }


}