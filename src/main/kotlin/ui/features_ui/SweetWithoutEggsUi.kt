package org.example.ui.features_ui

import org.example.logic.use_case.SweetWithNoEggsUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.utils.Colors

class SweetWithoutEggsUi(
    private val sweetWithNoEggs: SweetWithNoEggsUseCase,
    private val viewer: Viewer,
    private val reader: Reader,
) {
    private var suggestion: Recipe? = null

    fun show() {
        suggestionSweetRecipeWithNoEggs()
        printSweetWithNoEggs()
        while (true) {
            val choice = reader.readInput()?.toIntOrNull()
            when (choice) {
                1 -> suggestion?.let { viewer.printPlainText(RecipeFormatter.format(it)) }
                0 -> break
                else -> {
                    suggestionSweetRecipeWithNoEggs()
                    printSweetWithNoEggs()
                }
            }
        }
    }

    private fun suggestionSweetRecipeWithNoEggs() {
        suggestion = sweetWithNoEggs.findSweetsFreeEggs()
    }

    private fun printSweetWithNoEggs() {
        viewer.printCorrectOutput("Suggested Sweet: ${suggestion?.name}")
        viewer.printCorrectOutput("Description: ${suggestion?.description}")
        viewer.printInfoLine("If you like this sweet, enter 1.")
        viewer.printInfoLine("If you want to see another sweet, enter anything else:")
        viewer.printInfoLine("If you want to go out press 0. ")
    }

}