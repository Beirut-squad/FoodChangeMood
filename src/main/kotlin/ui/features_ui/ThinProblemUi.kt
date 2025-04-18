package org.example.ui.features_ui

import org.example.logic.use_case.ThinProblemUseCase
import org.example.utils.Colors

class ThinProblemUi(
    private val thinProblem: ThinProblemUseCase,
    private val colors: Colors
) {
    fun show() {
        while (true) {
            val suggestion = thinProblem.findThinProblem()
            printThinProblem()
            val choice = readln().toIntOrNull()
            if (choice == 1) {
                suggestion?.let { println(colors.green("\t\t $it")) }
            } else break
        }
    }

    private fun printThinProblem() {
        val suggestion = thinProblem.findThinProblem()
        println(colors.green("Suggested Meal: ${suggestion?.name}"))
        println(colors.green("Description: ${suggestion?.description}"))
        println(colors.green("Calories: ${suggestion?.nutrition?.calories}"))
        println(colors.green("Like it? Enter 1"))
        println(colors.green("Want another? Enter anything else:"))
    }
}