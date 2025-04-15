package org.example

import org.example.di.dataModule
import org.koin.core.context.startKoin
import org.example.data.CsvParser
import org.example.data.CsvReader
import org.example.data.FileNames.FOOD_CSV_FILE
import org.example.data.FileNames.PROCESSED_CSV_FILE
import org.example.data.RecipesRepositoryCsvImpl
import org.example.logic.KetoDiet
import org.example.model.getKetoScore
import java.io.File

fun main() {
    startKoin {
        modules(dataModule)
    }
}
