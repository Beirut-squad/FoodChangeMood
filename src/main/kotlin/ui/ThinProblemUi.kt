package org.example.ui

import org.example.logic.ThinProblemUseCase

class ThinProblemUi(
    private val thinProblem: ThinProblemUseCase,

    ) {
     fun show() {

        while (true) {
            val suggestion = thinProblem.findThinProblem()
            printThinProblem()
            val choice = readln().toIntOrNull()
            if (choice == 1) {
                suggestion?.let { println("\t\t $it") }
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