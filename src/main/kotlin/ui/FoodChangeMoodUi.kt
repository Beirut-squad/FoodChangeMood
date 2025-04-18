package org.example.ui

class FoodChangeMoodUi(
    private val easyFoodSuggestionUI: EasyFoodSuggestionUI,
    private val iraqiMealsUi: IraqiMealsUi,
    private val gymHelperUi: GymHelperUi,
    private val randomTopTenRecipesIncludePotatoUi: RandomTopTenRecipesIncludePotatoUi,
    private val searchRecipeByDateUi: SearchRecipeByDateUi,
    private val recipesTimeGuessGameUi: RecipesTimeGuessGameUi,
    private val sweetWithoutEggsUi: SweetWithoutEggsUi,
    private val ketoDietUi: KetoDietUi,
    private val italianGroupMealsUi: ItalianGroupMealsUi,

) {
    private var isRunning = true
    fun start() {
        showWelcomeMessage()
        presentAvailableFeatures()
    }

    private fun presentAvailableFeatures() {
        while (isRunning) {
            showOptions()
            val input = getUserInput()
            when (input) {
                3 -> iraqiMealsUi.show()
                4 -> easyFoodSuggestionUI.show()
                8 -> searchRecipeByDateUi.show()
                5 -> recipesTimeGuessGameUi.show()
                6 -> sweetWithoutEggsUi.show()
                7 -> ketoDietUi.show()
                9 -> gymHelperUi.show()
                12 -> randomTopTenRecipesIncludePotatoUi.show()
                15 -> italianGroupMealsUi.show()
                14 -> searchRecipeByDateUi.show()
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
        println("3- Iraq Food")
        println("4- Easy Food Suggestion ")
        println("5- Time Guess Game")
        println("6- Sweets with no eggs")
        println("7- Keto Diet Food Suggestion ")
        println("8- Search Recipe by add date")
        println("9- Gym Helper")
        println("12- I love potato ")
        println("14- Seafood with High Protein ")
        println("15- Italian Group Meals ")
        println("0- Enter 0 to exit the app")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }


}



