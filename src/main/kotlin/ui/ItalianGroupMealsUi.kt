package org.example.ui

import org.example.logic.ItalianGroupMealsUseCase

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