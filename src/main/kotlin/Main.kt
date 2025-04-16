package org.example

import org.example.data.CsvParser
import org.example.data.CsvReader
import org.example.data.FileNames.FOOD_CSV_FILE
import org.example.data.FileNames.PROCESSED_CSV_FILE
import org.example.data.RecipesRepositoryCsvImpl
import org.example.di.dataModule
import org.example.di.useCaseModule
import org.example.logic.EasyFoodSuggestionUseCase
import org.example.ui.FoodChangeMoodUi
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import java.io.File

fun main() {
    startKoin {
        modules(dataModule , useCaseModule )
    }

    val ui : FoodChangeMoodUi = getKoin().get()
    ui.start()
}