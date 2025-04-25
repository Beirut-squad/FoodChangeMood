package org.example.ui.features_ui

import org.example.logic.use_case.ThinProblemUseCase
import org.example.model.Recipe
import org.example.ui.Reader
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.utils.Colors

class ThinProblemUi(
//    private val thinProblem: ThinProblemUseCase,
//    private val colors: Colors
    private val thinProblem: ThinProblemUseCase,
    private val viewer: Viewer,
    private val reader: Reader
) {
    fun show() {
        while (true) {
            val suggestion = thinProblem.findThinProblem()
            printThinProblem()
            val choice = reader.readInput()?.toIntOrNull()
            if (choice == 1) {
                suggestion?.let {
                    viewer.printCorrectOutput(RecipeFormatter.format(it))
                }
            } else break
        }
    }

//    fun show() {
//        while (true) {
//            val suggestion = thinProblem.findThinProblem()
//            printThinProblem()
//            val choice = readln().toIntOrNull()
//            if (choice == 1) {
//                suggestion?.let { println(colors.green(RecipeFormatter.format(it))) }
//            } else break
//        }
//    }
private fun printThinProblem() {
    val suggestion = thinProblem.findThinProblem()
    suggestion?.let {
        if (suggestion.name.isNullOrEmpty()|| suggestion.description.isNullOrEmpty() || suggestion.nutrition?.calories == null) {
            viewer.printError("Meal suggestion has missing information.")}
        else{
            viewer.printCorrectOutput("Suggested Meal: ${suggestion.name}")
            viewer.printCorrectOutput("Description: ${suggestion.description}", true)
            viewer.printCorrectOutput("Calories: ${suggestion.nutrition?.calories}" )
            viewer.printCorrectOutput("Like it? Enter 1")
            viewer.printCorrectOutput("Want another? Enter anything else:")
            }

    } ?: run{
        viewer.printError("No meal suggestion available.")
    }

}

//    private fun printThinProblem() {
//        val suggestion = thinProblem.findThinProblem()
//        println(colors.green("Suggested Meal: ${suggestion?.name}"))
//        println(colors.green("Description: ${suggestion?.description}"))
//        println(colors.green("Calories: ${suggestion?.nutrition?.calories}"))
//        println(colors.green("Like it? Enter 1"))
//        println(colors.green("Want another? Enter anything else:"))
//    }


}