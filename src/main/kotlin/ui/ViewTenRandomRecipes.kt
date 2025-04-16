package org.example.ui

import org.example.logic.GetTenRandomRecipesIncludePotato

class ViewTenRandomRecipes(
    private val getTenRandomRecipesIncludePotato: GetTenRandomRecipesIncludePotato
) {
    fun viewOutput()
    {
        println("I Love potato")
        println("\tThey are different ten recipes include your favourite ingredients Potato")
        val potatoMeals= getTenRandomRecipesIncludePotato.findPotatoMeals()
        potatoMeals.forEach {
            println("\t\t $it")
        }
        //println(potatoMeals.size)
    }

}