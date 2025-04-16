package org.example.di

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.SweetWithNoEggsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { EasyFoodSuggestionUseCase(get()) }
    single { SweetWithNoEggsUseCase(get()) }
}