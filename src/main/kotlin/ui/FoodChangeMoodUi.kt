package org.example.ui


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
    private val globalFoodCultureUI: GlobalFoodCultureUI,
    private val viewer: Viewer,
    private val reader: Reader
) {

    fun start() {
        showWelcomeMessage()
        presentAvailableFeatures()
    }

    private fun presentAvailableFeatures() {
        while (true) {
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
                    viewer.printGoodbyeMessage("Goodbye :)")
                    break
                }

                else -> viewer.printError("Invalid input, try again")
            }
        }
    }

    private fun showWelcomeMessage() {
        viewer.printWelcomeMessage("Welcome to Food Change Mood App")
    }

    private fun showOptions() {
        viewer.printOption("\n=== Please enter the number of the service you want: ")
        viewer.printOption("1- Get Quick and Healthy Meals")
        viewer.printOption("2- Smart Meal Search (By name) ")
        viewer.printOption("3- Iraq Food")
        viewer.printOption("4- Easy Food Suggestion")
        viewer.printOption("5- Time Guess Game")
        viewer.printOption("6- Sweets with no eggs")
        viewer.printOption("7- Keto Diet Food Suggestion ")
        viewer.printOption("8- Search Recipe by add date")
        viewer.printOption("9- Gym Helper")
        viewer.printOption("10- Global Food Culture")
        viewer.printOption("11- Ingredients Guessing Game")
        viewer.printOption("12- I love potato ")
        viewer.printOption("13- Thin problem Suggestion ")
        viewer.printOption("14- Seafood with High Protein ")
        viewer.printOption("15- Italian Group Meals ")
        viewer.printExitOption("0- Enter 0 to exit the app")
    }

    private fun getUserInput(): Int? {
        return reader.readInput()?.toIntOrNull()
    }

}
