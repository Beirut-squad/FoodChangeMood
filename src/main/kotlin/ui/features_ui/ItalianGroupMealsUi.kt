package org.example.ui.features_ui

import org.example.error.RecipeNotFoundException
import org.example.logic.use_case.ItalianGroupMealsUseCase
import org.example.model.Recipe
import org.example.ui.Viewer
import kotlin.collections.forEachIndexed

class ItalianGroupMealsUi(
    private val italianGroupMealsUseCase: ItalianGroupMealsUseCase,
    private val viewer: Viewer
) {
    fun show() {
        try {
            viewer.printLoader("Loading...")
            val meals = italianGroupMealsUseCase.getItalianGroupMeals()
            displayMeals(meals)
        } catch (exception: RecipeNotFoundException) {
            viewer.printError("${exception.message}")
        }
    }

    private fun displayMeals(meals: List<Recipe>) {
        viewer.printTitle("Italian Meals For Groups:")
        meals.forEachIndexed { index, recipe ->
            viewer.printCorrectOutput("${index + 1}. ${recipe.name} ")
        }
    }
}