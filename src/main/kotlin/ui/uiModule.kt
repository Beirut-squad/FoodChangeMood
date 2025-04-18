package org.example.ui

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val uiModule = module {
    singleOf(::FoodChangeMoodUi)
    singleOf(::IngredientsGuessingGameUi)
}