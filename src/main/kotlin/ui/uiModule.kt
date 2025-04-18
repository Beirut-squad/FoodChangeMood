package org.example.ui

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val uiModule = module {
    singleOf(::FoodChangeMoodUi)
    singleOf(::EasyFoodSuggestionUI)
    singleOf(::IraqiMealsUi)
    singleOf(::SweetWithoutEggsUi)
    singleOf(::KetoDietUi)
    singleOf(::GymHelperUi)
    singleOf(::RandomTopTenRecipesIncludePotatoUi)
    singleOf(::RecipesTimeGuessGameUi)
    singleOf(::ItalianGroupMealsUi)
    singleOf(::SeafoodWithHighProteinUi)
    singleOf(::SearchRecipeByDateUi)
    singleOf(::HealthyFoodRecipesUi)
    singleOf(::ThinProblemUi)
}