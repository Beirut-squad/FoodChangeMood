package org.example.ui

import org.example.logic.SweetWithNoEggsUseCase

class SweetWithoutEggsUi(
    private val sweetWithNoEggs: SweetWithNoEggsUseCase
) {
     fun show() {
        while (true) {
            val suggestion = sweetWithNoEggs.findSweetsFreeEggs()
            printSweetWithNoEggs()
            val choice = readln().toIntOrNull()
            when(choice) {
                1 -> suggestion?.let { println("$it") }
                0 -> break
                else -> printSweetWithNoEggs()
            }
        }
    }
    private fun printSweetWithNoEggs(){
        val suggestion = sweetWithNoEggs.findSweetsFreeEggs()
        println("Suggested Sweet: ${suggestion?.name}")
        println("Description: ${suggestion?.description}")
        println("If you like this sweet, enter 1.")
        println("If you want to see another sweet, enter anything else:")
        println("If you want to go out press 0. ")
    }

}