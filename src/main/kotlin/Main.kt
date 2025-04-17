package org.example

import org.example.di.dataModule
import org.example.di.useCaseModule
import org.example.ui.FoodChangeMoodUi
import org.example.ui.uiModule
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun main() {
    startKoin {
        modules(dataModule , useCaseModule, uiModule )
    }

    val ui : FoodChangeMoodUi = getKoin().get()
//    ui.start()

    ui.launchGymHelperUi()
}