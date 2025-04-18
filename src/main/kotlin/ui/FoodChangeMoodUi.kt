package org.example.ui

import org.example.logic.KetoDiet
import org.example.logic.Validator
import org.example.logic.*
import org.example.logic.use_case.GymHelperUseCase
import org.example.model.Recipe
import Utils.checkDateFormat
import org.example.error.NoRecipesFoundForTheGivenDateException
import org.example.error.RecipeNotFoundException
import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.HealthyRecipesUseCase
import org.example.logic.RandomTenRecipesIncludePotatoUseCase
import org.example.logic.ThinProblemUseCase
import org.example.logic.IraqiMealsUseCase
import org.example.model.Nutrition
import Colors
import org.example.logic.GameFeedback
import org.example.logic.RecipeTimeGuessGame

import org.example.logic.SearchRecipeByDateUseCase
import java.text.ParseException
import java.time.format.DateTimeParseException


class FoodChangeMoodUi(
    private val iraqiMealsUi: IraqiMealsUi,
    private val easyFoodSuggestionUI: EasyFoodSuggestionUI,
    private val searchRecipeByDateUi: SearchRecipeByDateUi,
    private val sweetWithoutEggsUi: SweetWithoutEggsUi,
    private val thinProblemUi: ThinProblemUi,
    private val ketoDietUi: KetoDietUi,
    private val gymHelperUi: GymHelperUi,
    private val healthyFoodRecipesUi: HealthyFoodRecipesUi,
    private val seafoodWithHighProteinUi: SeafoodWithHighProteinUi,
    private val randomTopTenRecipesIncludePotatoUi: RandomTopTenRecipesIncludePotatoUi,
    private val italianGroupMealsUi: ItalianGroupMealsUi,
    private val recipesTimeGuessGameUi: RecipesTimeGuessGameUi,
) {
    private val colors = Colors()

    var isRunning = true
    fun start() {
        showWelcomeMessage()
        presentAvailableFeatures()
    }

    private fun presentAvailableFeatures() {
        var isRunning = true
        while (isRunning) {
            showOptions()
            val input = getUserInput()
            when (input) {
                1 -> healthyFoodRecipesUi.show()
                3 -> iraqiMealsUi.show()
                4 -> easyFoodSuggestionUI.show()
                8 -> searchRecipeByDateUi.show()
                5 -> recipesTimeGuessGameUi.show()
                6 -> sweetWithoutEggsUi.show()
                7 -> ketoDietUi.show()
                9 -> gymHelperUi.show()
                12 -> randomTopTenRecipesIncludePotatoUi.show()
                13 -> thinProblemUi.show()
                14 -> seafoodWithHighProteinUi.show()
                15 -> italianGroupMealsUi.show()
                0 -> {
                    println("Goodbye :)")
                    isRunning = false
                }

                else -> println("Invalid input, try again")
            }
        }
    }

    private fun showWelcomeMessage() {
        println("Welcome to Food Change Mood App")
    }

    private fun showOptions() {
        println("\n=== Please enter the number of the service you want: ")
        println("1- Get Quick and Healthy Meals")
        println("3- Iraq Food")
        println("4- Easy Food Suggestion ")
        println("5- Time Guess Game")
        println("6- Sweets with no eggs")
        println("7- Keto Diet Food Suggestion ")
        println("8- Search Recipe by add date")
        println("9- Gym Helper")
        println("12- I love potato ")
        println("13- Thin problem Suggestion ")
        println("14- Seafood with High Protein ")
        println("15- Italian Group Meals ")
        println("0- Enter 0 to exit the app")
    }


    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }





}



