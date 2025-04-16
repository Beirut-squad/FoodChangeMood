package org.example.di

import org.example.logic.RecipeTimeGuessGame
import org.koin.dsl.module

val useCasesModule = module {
    single { RecipeTimeGuessGame(get()) }
}
