package org.example.ui

import org.example.logic.RandomTenRecipesIncludePotatoUseCase

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