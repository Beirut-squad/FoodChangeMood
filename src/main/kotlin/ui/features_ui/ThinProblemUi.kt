package org.example.ui.features_ui

import org.example.logic.use_case.ThinProblemUseCase
import org.example.ui.RecipeFormatter

class ThinProblemUi(
    private val thinProblem: ThinProblemUseCase,

    ) {
     fun show() {

        while (true) {
            val suggestion = thinProblem.findThinProblem()
            printThinProblem()
            val choice = readln().toIntOrNull()
            if (choice == 1) {
                suggestion?.let { println(RecipeFormatter.format(it)) }
            } else break
        }
    }

    private fun printThinProblem() {
        val suggestion = thinProblem.findThinProblem()
        println("Suggested Meal: ${suggestion?.name}")
        println("Description: ${suggestion?.description}")
        println("Calories: ${suggestion?.nutrition?.calories}")
        println("Like it? Enter 1")
        println("Want another? Enter anything else:")
    }
}