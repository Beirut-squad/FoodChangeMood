package org.example

import org.example.di.dataModule
import org.example.logic.di.useCasesModule
import org.example.logic.use_case.GymHelperUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(
            dataModule,
            useCasesModule
        )
    }

    val gymHelperUseCase: GymHelperUseCase = getKoin().get()

    println(gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(
        calories = 5f,
        protein = 1.5f
    ))
}
