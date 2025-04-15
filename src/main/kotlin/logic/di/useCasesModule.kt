package org.example.logic.di

import org.example.logic.use_case.GymHelperUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single {
        GymHelperUseCase(get())
    }
}