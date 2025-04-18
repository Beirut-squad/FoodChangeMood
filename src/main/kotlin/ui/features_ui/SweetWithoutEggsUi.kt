package org.example.ui.features_ui

import org.example.logic.use_case.SweetWithNoEggsUseCase
import org.example.ui.RecipeFormatter
import org.example.utils.Colors

class SweetWithoutEggsUi(
    private val sweetWithNoEggs: SweetWithNoEggsUseCase,
    private val colors: Colors
) {
     fun show() {
        while (true) {
            val suggestion = sweetWithNoEggs.findSweetsFreeEggs()
            printSweetWithNoEggs()
            val choice = readln().toIntOrNull()
            when(choice) {
                1 -> suggestion?.let { println(RecipeFormatter.format(it)) }
                0 -> break
                else -> printSweetWithNoEggs()
            }
        }
    }
    private fun printSweetWithNoEggs(){
        val suggestion = sweetWithNoEggs.findSweetsFreeEggs()
        println(colors.green("Suggested Sweet: ${suggestion?.name}"))
        println(colors.green("Description: ${suggestion?.description}"))
        println(colors.yellow("If you like this sweet, enter 1."))
        println(colors.yellow("If you want to see another sweet, enter anything else:"))
        println(colors.yellow("If you want to go out press 0. "))
    }

}