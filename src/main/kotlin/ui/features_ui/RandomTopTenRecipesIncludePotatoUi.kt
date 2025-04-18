package org.example.ui.features_ui

import org.example.logic.use_case.RandomTenRecipesIncludePotatoUseCase
import org.example.utils.Colors

class RandomTopTenRecipesIncludePotatoUi(
    private val randomTenRecipesIncludePotatoUseCase: RandomTenRecipesIncludePotatoUseCase,
    private val colors: Colors
) {
     fun show()
    {
        val potatoMeals= randomTenRecipesIncludePotatoUseCase.findPotatoMeals()
        potatoMeals.forEach {
            println(colors.green("$it"))
        }
    }

}