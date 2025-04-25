package org.example.ui.features_ui

import org.example.logic.use_case.KetoDietUseCase
import org.example.model.Nutrition
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.Viewer
import org.example.utils.Colors
import ui.Display

class KetoDietUi(
    private val ketoDiet: KetoDietUseCase,
    private val viewer: Viewer,
    private val reader: Reader
) : Display{
     override fun show(){
        viewer.printTitle("Welcome to Keto Meal Suggester ")
          while(true){
            viewer.printInfoLine("1. Suggest a Keto Recipe \n2. Go Back ")
            val input: String? = reader.readInput()
            when(input){
                "1" -> {
                    suggestRecipeForUser()
                    continue
                }
                "2" -> break
                else -> viewer.printError("enter a valid number")
            }
        }
    }

    private fun suggestRecipeForUser(){
        val recipe = ketoDiet.suggestKetoRecipe()
        viewer.printCorrectOutput("Meal name: ${recipe.name}")
        viewer.printInfoLine("Do you want to proceed with Recipe details ? (Y,n) ")
        val input: String? = reader.readInput()
        if (input == "Y"){
            viewer.printRecipeDetails(recipe)
        }
    }
}


