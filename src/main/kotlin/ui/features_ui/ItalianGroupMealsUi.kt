package org.example.ui.features_ui

import org.example.logic.use_case.ItalianGroupMealsUseCase

class ItalianGroupMealsUi (
    private val italianGroupMealsUseCase: ItalianGroupMealsUseCase,

    ){

     fun show(){
        italianGroupMealsUseCase
            .getItalianGroupMeals()
            .forEachIndexed { index, recipe ->
                println("${index + 1}. ${recipe.name} ")

            }
    }
}