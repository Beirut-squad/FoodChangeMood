package org.example.ui.features_ui

import org.example.logic.use_case.RandomTenRecipesIncludePotatoUseCase

class RandomTopTenRecipesIncludePotatoUi(
    private val randomTenRecipesIncludePotatoUseCase: RandomTenRecipesIncludePotatoUseCase
) {
     fun show()
    {
        val potatoMeals= randomTenRecipesIncludePotatoUseCase.findPotatoMeals()
        potatoMeals.forEach {
            println("$it")
        }
    }

}