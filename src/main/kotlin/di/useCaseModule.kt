package org.example.di

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.ItalianGroupMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { EasyFoodSuggestionUseCase(get()) }
    single { ItalianGroupMealsUseCase(get()) }
}