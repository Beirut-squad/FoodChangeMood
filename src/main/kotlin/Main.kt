package org.example

import org.example.di.dataModule
import org.example.logic.RecipesRepository
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(dataModule)
    }

    val recipesRepository: RecipesRepository = getKoin().get()

    println(recipesRepository.getAllRecipes().first())
}
