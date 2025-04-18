package org.example.di

import org.example.data.CsvParser
import org.example.data.CsvReader
import org.example.data.FileNames.FOOD_CSV_FILE
import org.example.data.RecipesRepositoryCsvImpl
import org.example.logic.HealthyRecipesUseCase
import org.example.logic.RecipesRepository
import org.example.ui.FoodChangeMoodUi
import org.koin.dsl.module
import java.io.File

val dataModule = module {
    single {
        CsvParser()
    }

    single {
        CsvReader(
            csvInputFile = File(FOOD_CSV_FILE),
        )
    }

    single<RecipesRepository> {
        RecipesRepositoryCsvImpl(get(), get())
    }


    single { FoodChangeMoodUi(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}
