package org.example

import org.example.di.dataModule
import org.example.logic.di.useCasesModule
import org.example.ui.FoodChangeMoodUi
import org.example.ui.uiModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(
            dataModule,
            useCasesModule,
            uiModule
        )
    }

    val foodChangeMoodUi: FoodChangeMoodUi = getKoin().get()

    foodChangeMoodUi.launchGymHelperUi()
}
