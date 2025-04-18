package org.example.ui.features_ui

import org.example.logic.use_case.RandomTenRecipesIncludePotatoUseCase
import org.example.ui.RecipeFormatter

class RandomTopTenRecipesIncludePotatoUi(
    private val randomTenRecipesIncludePotatoUseCase: RandomTenRecipesIncludePotatoUseCase
) {
     fun show()
    {
        val potatoMeals= randomTenRecipesIncludePotatoUseCase.findPotatoMeals()
        potatoMeals.forEach {
            println(RecipeFormatter.format(it))
        }
    }

}