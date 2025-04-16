package org.example.di

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.RandomTenRecipesIncludePotatoUseCase
import org.example.logic.SearchRecipeByDateUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { EasyFoodSuggestionUseCase(get()) }
    single { RandomTenRecipesIncludePotatoUseCase(get()) }
    single { SearchRecipeByDateUseCase(get()) }
}