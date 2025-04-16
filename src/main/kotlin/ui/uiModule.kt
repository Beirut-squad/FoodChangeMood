package org.example.ui

import org.koin.dsl.module

val uiModule = module {
    single {
        FoodChangeMoodUi(get(), get(), get())
    }
}