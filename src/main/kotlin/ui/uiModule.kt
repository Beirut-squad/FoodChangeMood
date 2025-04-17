package org.example.ui

import org.koin.dsl.module

val uiModule = module {
    singleOf(::FoodChangeMoodUi)
}