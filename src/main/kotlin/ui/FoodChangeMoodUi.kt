package org.example.ui


import org.example.ui.features_ui.*
import org.example.utils.Colors

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
        println(colors.purple("\n=== Please enter the number of the service you want: "))
        printOption("1- Get Quick and Healthy Meals")
        printOption("2- Smart Meal Search (By name) ")
        printOption("3- Iraq Food")
        printOption("4- Easy Food Suggestion")
        printOption("5- Time Guess Game")
        printOption("6- Sweets with no eggs")
        printOption("7- Keto Diet Food Suggestion ")
        printOption("8- Search Recipe by add date")
        printOption("9- Gym Helper")
        printOption("10- Global Food Culture")
        printOption("11- Ingredients Guessing Game")
        printOption("12- I love potato ")
        printOption("13- Thin problem Suggestion ")
        printOption("14- Seafood with High Protein ")
        printOption("15- Italian Group Meals ")
        println(colors.red("0- Enter 0 to exit the app"))
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

    private fun printOption(text: String) {
        println(colors.blue(text))
    }
}
