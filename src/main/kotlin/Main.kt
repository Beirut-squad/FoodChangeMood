package org.example

import org.example.di.dataModule
import org.example.ui.FoodChangeMoodUi
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(dataModule)
    }

    val ui: FoodChangeMoodUi = getKoin().get()
    ui.start()
}
