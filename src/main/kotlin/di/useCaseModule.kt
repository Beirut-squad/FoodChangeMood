package org.example.di

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.ItalianGroupMealsUseCase
import org.example.logic.RandomTenRecipesIncludePotatoUseCase
import org.example.logic.Validator
import org.example.logic.use_case.GymHelperUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { EasyFoodSuggestionUseCase(get()) }
    single { ItalianGroupMealsUseCase(get()) }
    single {
        Validator()
    }
    single {
        GymHelperUseCase(get())
    }
    single { RandomTenRecipesIncludePotatoUseCase(get()) }
}