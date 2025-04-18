package org.example.di

import org.example.logic.HealthyRecipesUseCase
import org.koin.core.module.Module
import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.IraqiMealsUseCase
import org.example.logic.Validator
import org.example.logic.*
import org.example.logic.use_case.GymHelperUseCase
import org.example.ui.GlobalFoodCultureUI
import org.example.logic.RandomTenRecipesIncludePotatoUseCase
import org.example.logic.ThinProblemUseCase
import org.example.logic.SearchRecipeByDateUseCase
import org.example.logic.SeafoodWithHighProteinUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    single { EasyFoodSuggestionUseCase(get()) }
    single{ HealthyRecipesUseCase(get()) }
    single {
        Validator()
    }
    single {
        GymHelperUseCase(get())
    }
    single { SweetWithNoEggsUseCase(get()) }
    single { Validator() }
    single { GymHelperUseCase(get())}
    single { RandomTenRecipesIncludePotatoUseCase(get()) }
    single { IraqiMealsUseCase(get()) }
    single { RecipeTimeGuessGame(get()) }
    single { ItalianGroupMealsUseCase(get()) }
    single { KetoDiet(get()) }
    single { GlobalFoodCultureUseCase(get()) }
    single { GlobalFoodCultureUI(get(),get()) }
    single { SearchRecipeByDateUseCase(get()) }
    single { SeafoodWithHighProteinUseCase(get()) }

    singleOf(::IngredientGuessingGameUseCase)

    single { ThinProblemUseCase(get()) }

}