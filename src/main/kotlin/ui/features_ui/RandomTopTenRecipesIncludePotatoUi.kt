package org.example.ui.features_ui

import org.example.logic.use_case.RandomTenRecipesIncludePotatoUseCase
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.utils.Colors

class RandomTopTenRecipesIncludePotatoUi(
    private val randomTenRecipesIncludePotatoUseCase: RandomTenRecipesIncludePotatoUseCase,
        private val viewer: Viewer
) {
     fun show()
    {
        val potatoMeals= randomTenRecipesIncludePotatoUseCase.findPotatoMeals()
        if (potatoMeals.isEmpty()){
            viewer.printError("Recipe not found")
        }else{

            potatoMeals.forEach {
                val formatted = RecipeFormatter.format(it)
                viewer.printCorrectOutput(formatted,true)
            }
        }
    }

}