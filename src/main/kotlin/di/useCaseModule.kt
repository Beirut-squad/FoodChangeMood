package org.example.di

import org.example.logic.use_case.HealthyRecipesUseCase
import org.example.logic.use_case.IraqiMealsUseCase
import org.example.logic.Validator
import org.example.logic.use_case.RandomTenRecipesIncludePotatoUseCase
import org.example.logic.use_case.ThinProblemUseCase
import org.example.logic.use_case.SearchRecipeByDateUseCase
import org.example.logic.use_case.SeafoodWithHighProteinUseCase
import org.example.logic.use_case.*
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
    single { GymHelperUseCase(get())}
    single { RandomTenRecipesIncludePotatoUseCase(get()) }
    single { IraqiMealsUseCase(get()) }
    single { RecipeTimeGuessGameUseCase(get()) }
    single { ItalianGroupMealsUseCase(get()) }
    single { KetoDietUseCase(get()) }
    single { GlobalFoodCultureUseCase(get()) }
    single { SearchRecipeByDateUseCase(get()) }
    single { SeafoodWithHighProteinUseCase(get()) }

    singleOf(::IngredientGuessingGameUseCase)

    singleOf(::SearchByNameUseCase)

    singleOf(::Trie)

    single { ThinProblemUseCase(get()) }

}