package org.example.di

import org.example.ui.*
import org.example.ui.features_ui.*
import org.example.utils.Colors
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
    singleOf(::GlobalFoodCultureUI)
    singleOf(::IngredientsGuessingGameUi)
    singleOf(::SearchByNameUI)
    singleOf(::Colors)
    singleOf(::Reader)
    singleOf(::Viewer)

}