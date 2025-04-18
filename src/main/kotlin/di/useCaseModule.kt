package org.example.di

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.IraqiMealsUseCase
import org.example.logic.Validator
import org.example.logic.*
import org.example.logic.use_case.GymHelperUseCase
import org.example.logic.RandomTenRecipesIncludePotatoUseCase
import org.example.logic.SeafoodWithHighProteinUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { EasyFoodSuggestionUseCase(get()) }
    single { SweetWithNoEggsUseCase(get()) }
    single { Validator() }
    single { GymHelperUseCase(get())}
    single { RandomTenRecipesIncludePotatoUseCase(get()) }
    single { IraqiMealsUseCase(get()) }
    single { RecipeTimeGuessGame(get()) }
    single { ItalianGroupMealsUseCase(get()) }
    single { KetoDiet(get()) }
    single { SeafoodWithHighProteinUseCase(get()) }
}