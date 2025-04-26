package org.example.di

import org.example.logic.RecipesRepository
import org.example.logic.use_case.SearchByNameUseCase
import org.example.utils.SearchByNameAlgo.Trie
import org.example.ui.*
import org.example.ui.features_ui.*
import org.example.utils.Colors
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val uiModule = module {

    //  Trie with names from the repo
    single {
        val repo = get<RecipesRepository>()
        val trie = Trie()
        repo.getAllRecipes().forEach {
            val name = it.name?.lowercase()?.trim()
            if (!name.isNullOrEmpty()) {
                trie.insert(name)
            }
        }
        trie
    }
    // UseCase that depends on the Trie and Repo
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
    singleOf(::Viewer)
    singleOf(::Reader)
}
