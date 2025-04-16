package org.example

import org.example.data.CsvParser
import org.example.data.CsvReader
import org.example.data.FileNames.FOOD_CSV_FILE
import org.example.data.FileNames.PROCESSED_CSV_FILE
import org.example.data.RecipesRepositoryCsvImpl
import org.example.di.dataModule
import org.example.logic.EasyFoodSuggestionUseCase
import org.koin.core.context.startKoin
import java.io.File

fun main() {
//    startKoin {
//        modules(dataModule)
//    }


    val reader = CsvReader(File(FOOD_CSV_FILE), File(PROCESSED_CSV_FILE))
    val parser = CsvParser()
    val repository = RecipesRepositoryCsvImpl(reader, parser)
    val easyFoodUseCase = EasyFoodSuggestionUseCase(repository)

    val easyMeals = easyFoodUseCase.getTenEasyFoodSuggestions()
    easyMeals.forEachIndexed { index, recipe ->
        println("${index + 1}. ${recipe.name} - ${recipe.minutes} min - ${recipe.ingredients?.size} ingredients - ${recipe.steps?.size} steps")

    }

}