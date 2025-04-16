package org.example.di

import org.example.logic.HealthyRecipesUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseModule = module {
    single{ HealthyRecipesUseCase(get()) }
}