package org.example.ui

import org.example.logic.GetTenRandomRecipesIncludePotatoUseCase

class ViewTenRandomRecipesUseCase(
    private val getTenRandomRecipesIncludePotato: GetTenRandomRecipesIncludePotatoUseCase
) {
    fun viewOutput()
    {
        println("I Love potato")
        println("They are different ten recipes include your favourite ingredients Potato")
        val potatoMeals= getTenRandomRecipesIncludePotato.findPotatoMeals()
        potatoMeals.forEach {
            println("\t\t $it")
        }
    }
}