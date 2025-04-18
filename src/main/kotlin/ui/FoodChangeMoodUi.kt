package org.example.ui


import Colors
import org.example.ui.features_ui.*

class FoodChangeMoodUi(
    private val iraqiMealsUi: IraqiMealsUi,
    private val searchByNameUI: SearchByNameUI,
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
    private val ingredientsGuessingGameUi: IngredientsGuessingGameUi,
    private val globalFoodCultureUI: GlobalFoodCultureUI
) {
    private val colors = Colors()

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
                2 -> searchByNameUI.show()
                3 -> iraqiMealsUi.show()
                4 -> easyFoodSuggestionUI.show()
                8 -> searchRecipeByDateUi.show()
                5 -> recipesTimeGuessGameUi.show()
                6 -> sweetWithoutEggsUi.show()
                7 -> ketoDietUi.show()
                9 -> gymHelperUi.show()
                10 -> globalFoodCultureUI.show()
                11 -> ingredientsGuessingGameUi.show()
                12 -> randomTopTenRecipesIncludePotatoUi.show()
                13 -> thinProblemUi.show()
                14 -> seafoodWithHighProteinUi.show()
                15 -> italianGroupMealsUi.show()
                0 -> {
                    println(colors.yellow("Goodbye :)"))
                    isRunning = false
                }

                else -> println(colors.red("Invalid input, try again"))
            }
        }
    }

    private fun showWelcomeMessage() {
        println(colors.cyan("Welcome to Food Change Mood App"))
    }

    private fun showOptions() {
        println("\n=== Please enter the number of the service you want: ")
        println("1- Get Quick and Healthy Meals")
        println("2- Smart Meal Search (By name) ")
        println("3- Iraq Food")
        println("4- Easy Food Suggestion ")
        println("5- Time Guess Game")
        println("6- Sweets with no eggs")
        println("7- Keto Diet Food Suggestion ")
        println("8- Search Recipe by add date")
        println("9- Gym Helper")
        println("10- Global Food Culture")
        println("11- Ingredients Guessing Game")
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



