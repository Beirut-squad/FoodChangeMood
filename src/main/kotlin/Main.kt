package org.example

import org.example.di.dataModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(dataModule)
    }
}
