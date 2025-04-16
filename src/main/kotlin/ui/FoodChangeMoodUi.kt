package org.example.ui


class FoodChangeMoodUi(

) {
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
                1 -> launchExampleUseCase()
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
        println("1- Example useCase")
        println("0- Enter 0 to exit the app")
    }

    private fun launchExampleUseCase() {}

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}
