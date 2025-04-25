package org.example.logic.use_case

import org.example.error.NoRecipesFoundForTheGivenDateException
import org.example.error.RecipeNotFoundException
import org.example.model.Recipe
import utils.toDate
import org.example.logic.RecipesRepository
import java.time.LocalDate

class SearchRecipeByDateUseCase (
    private val recipesRepository: RecipesRepository
){
    fun searchRecipeByDate(enteredDate:String): List<Pair<String, String>>{
        val validDate = validateAndParseDate(enteredDate)
        return recipesRepository.getAllRecipes()
            .filter(::checkNoNullValue)
            .filter { it.submittedDate == validDate }
            .takeIf { it.isNotEmpty() }
            ?.map {recipe->
                (recipe.id)!! to recipe.name!!
            } ?: throw NoRecipesFoundForTheGivenDateException("No meals were found for the given date")
    }

    fun viewDetailsOfRecipeByID(recipeId: String): Recipe{
        return recipesRepository.getAllRecipes()
            .filter { it.id == recipeId }
            .let { it.getOrNull(0) ?: throw RecipeNotFoundException("Recipe Not Found")}
    }

    private fun checkNoNullValue(recipe: Recipe): Boolean{
        return recipe.name != null && recipe.id != null
    }

    private fun validateAndParseDate(enteredDate: String): LocalDate {
        val parsedDate = enteredDate.toDate()
        if (parsedDate > LocalDate.now()) {
            throw IllegalArgumentException("Date cannot be in the future")
        }
        return parsedDate
    }
}