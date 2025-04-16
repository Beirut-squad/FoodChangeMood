package org.example.logic.di

import org.example.logic.Validator
import org.example.logic.use_case.GymHelperUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single {
        Validator()
    }
    single {
        GymHelperUseCase(get())
    }
}