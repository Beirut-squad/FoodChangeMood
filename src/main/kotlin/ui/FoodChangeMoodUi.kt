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
import org.example.data.RecipesRepositoryCsvImpl
import org.example.logic.GameFeedback
import org.example.logic.RecipeTimeGuessGame
import org.example.logic.SearchRecipeByDateUseCase
import java.text.ParseException
import java.time.format.DateTimeParseException


class FoodChangeMoodUi(
    private val iraqiMealsUseCase: IraqiMealsUseCase,
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val healthyRecipesUseCase: HealthyRecipesUseCase,
    private val thinProblem: ThinProblemUseCase,
    private val searchRecipeByDateUseCase: SearchRecipeByDateUseCase,
    private val sweetWithNoEggs: SweetWithNoEggsUseCase,
    private val ketoDiet: KetoDiet,
    private val gymHelperUseCase: GymHelperUseCase,
    private val validator: Validator,
    private val recipeTimeGuessGame: RecipeTimeGuessGame,
    private val globalFoodCultureUI: GlobalFoodCultureUI,
    private val seafoodWithHighProteinUseCase: SeafoodWithHighProteinUseCase,
    private val randomTenRecipesIncludePotatoUseCase: RandomTenRecipesIncludePotatoUseCase,
    private val italianGroupMealsUseCase: ItalianGroupMealsUseCase,
    private val recipeTimeGuessGame: RecipeTimeGuessGame
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
                1 -> launchHealthyRecipes()
                3 -> presentIraqMeals()
                4 -> launchEasyFoodSuggestionUseCase()
                8 -> launchSearchRecipeByDateUseCase()
                5 -> launchGuessPrepTimeGame()
                6 -> launchSweetWithoutEggsUseCase()
                7 -> launchKetoDietUseCase()
                9 -> launchGymHelperUi()
                10 -> globalFoodCultureUI.displayCountryFoodCulture()
                12 -> launchRandomTenPotatoUseCase()
                13 -> launchThinProblemUseCase()
                14 -> launchSeafoodWithHighProteinUseCase()
                15 -> launchItalianGroupMeals()
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
        println("10- Explore Global Food Culture")
        println("12- I love potato ")
        println("13- Thin problem Suggestion ")
        println("14- Seafood with High Protein ")
        println("15- Italian Group Meals ")
        println("0- Enter 0 to exit the app")
    }

    private fun launchExampleUseCase() {}
    private fun launchEasyFoodSuggestionUseCase() {
        easyFoodSuggestionUseCase
            .getTenEasyFoodSuggestions()
            .forEachIndexed { index, recipe ->
                println("${index + 1}. ${recipe.name} - ${recipe.minutes} min - ${recipe.ingredients?.size} ingredients - ${recipe.steps?.size} steps")

            }
    }


    private fun launchHealthyRecipes() {
        println("Enter the number of meals you want")
        val count = getUserInput()

        if (count == null || !validator.validateRecipesCountInput(count)) {
            println("Invalid Input, Enter a Positive Number")
        } else
            displayRecipeInfo(count)
    }

    private fun displayRecipeInfo(count: Int) {
        println("Loading...")
        val healthyRecipes = healthyRecipesUseCase.getHealthyRecipes(count)
        if (healthyRecipes.isEmpty()) {
            println("No Recipes Available")
        } else healthyRecipes.forEach {
            println(RecipeFormatter.format(it))
        }
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

    fun launchGymHelperUi() {
        println("Gym helper: Get meals that match the protein and calories amounts you choose or close to them.")
        while (true) {
            print("Enter the amount of protein: ")
            val protein = readlnOrNull()

            print("Enter the amount of calories: ")
            val calories = readlnOrNull()

            if (validator.validateGymHelperInput(calories, protein)) {
                val recipes = gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(
                    calories = calories?.toFloat() ?: 0f,
                    protein = protein?.toFloat() ?: 0f
                )
                displayRecipesForGymHelper(recipes)
                break
            } else {
                println("Invalid input.")
            }
        }


    }

    private fun displayRecipesForGymHelper(recipes: List<Recipe>) {
        recipes.forEachIndexed { index, recipe ->
            displaySingleRecipeForGymHelper(recipe, index + 1)
            println()
        }
    }

    private fun displaySingleRecipeForGymHelper(recipe: Recipe, index: Int) {
        println("Meal $index: ${recipe.name}")

        println("Calories: ${recipe.nutrition?.calories ?: 0.0}, Protein: ${recipe.nutrition?.protein ?: 0.0}")

        recipe.ingredients?.let { displayIngredients(recipe.ingredients) }

        recipe.steps?.let { displaySteps(recipe.steps) }
    }

    private fun displayIngredients(ingredients: List<String>) {
        print("Ingredients: ")
        ingredients.forEach {
            print("$it, ")
        }
    }

    private fun displaySteps(steps: List<String>) {
        println("How to Make: ")
        steps.forEachIndexed { stepIndex, step ->
            print("Step ${stepIndex + 1}: ")
            println(step)
        }
    }

    private fun launchSweetWithoutEggsUseCase() {
        while (true) {
            val suggestion = sweetWithNoEggs.findSweetsFreeEggs()
            printSweetWithNoEggs()
            val choice = readln().toIntOrNull()
            when (choice) {
                1 -> suggestion?.let { println("$it") }
                0 -> break
                else -> printSweetWithNoEggs()
            }
        }
    }

    private fun printSweetWithNoEggs() {
        val suggestion = sweetWithNoEggs.findSweetsFreeEggs()
        println("Suggested Sweet: ${suggestion?.name}")
        println("Description: ${suggestion?.description}")
        println("If you like this sweet, enter 1.")
        println("If you want to see another sweet, enter anything else:")
        println("If you want to go out press 0. ")
    }

    private fun launchRandomTenPotatoUseCase() {
        val potatoMeals = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()
        potatoMeals.forEach {
            println("\t\t $it")
        }
    }

    private fun launchThinProblemUseCase() {

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


    private fun launchSearchRecipeByDateUseCase() {
        println("Enter the date for which you want to view recipes: example (2006-10-07)")
        try {
            readlnOrNull()?.let { inputDate ->
                inputDate.checkDateFormat()
                searchRecipeByDateUseCase.searchRecipeByDate(inputDate).forEach { idAndName ->
                    println("ID = ${idAndName.first} Recipe Name: ${idAndName.second}")
                }
                askUserIfHeWantDetailsOfRecipe()
            } ?: println("Please enter a valid date")
        } catch (parseException: ParseException) {
            println("Incorrect date format ,Please enter a valid date")
        } catch (dateTimeException: DateTimeParseException) {
            println("Incorrect date format ,Please enter a valid date")
        } catch (noRecipesFoundForTheGivenDateException: NoRecipesFoundForTheGivenDateException) {
            println(noRecipesFoundForTheGivenDateException.message)
        }
    }


    private fun askUserIfHeWantDetailsOfRecipe() {
        isRunning = false
        println("Do you want to get details of a specific recipe? (Y/N)")
        readlnOrNull()?.lowercase().let { answer ->
            when (answer) {
                "y" -> {
                    searchRecipeByID()
                }

                "n" -> {
                    isRunning = true
                }

                else -> {
                    println("Invalid choice")
                    askUserIfHeWantDetailsOfRecipe()
                }
            }
        }
    }

    private fun searchRecipeByID() {
        try {
            println("Enter the ID of the recipe whose details you want to see:")
            readlnOrNull()?.let { enteredID ->
                val idAsNumber = enteredID.toIntOrNull() ?: 0
                if (idAsNumber == 0) {
                    println("Enter valid id !")
                    askToBackToMainMenu()
                } else {
                    val recipe = searchRecipeByDateUseCase.viewDetailsOfRecipeByID(enteredID)
                    printRecipe(recipe)
                    isRunning = true
                }
            } ?: {
                println("Enter valid id !")
                askToBackToMainMenu()
            }
        } catch (e: RecipeNotFoundException) {
            // ask to exit from this menu to main menu
            println(e.message)
            askToBackToMainMenu()
        }
    }

    private fun askToBackToMainMenu() {
        println("Are you need to back to main menu ? (Y/N)")
        readlnOrNull()?.lowercase().let { answer ->
            when (answer) {
                "y" -> {
                    isRunning = true
                }

                "n" -> {
                    searchRecipeByID()
                }

                else -> {
                    println("Invalid choice")
                }
            }
        }
    }

    private fun printRecipe(recipe: Recipe) {
        println(
            "Recipe Details: ------------------------------------------------\nName: ${recipe.name}\n" +
                    "Minutes: ${recipe.minutes}\nContributor Id: ${recipe.contributorId}\n" +
                    "Submitted Date: ${recipe.submittedDate}\nTags:\n${recipe.tags}\n" +
                    "Nutrition:\nCalories = ${recipe.nutrition?.calories}\t" +
                    "Total Fat = ${recipe.nutrition?.totalFat}\t" +
                    "Sugar = ${recipe.nutrition?.sugar}\t" +
                    "Sodium = ${recipe.nutrition?.sodium}\t" +
                    "Protein = ${recipe.nutrition?.protein}\t" +
                    "Saturated Fat = ${recipe.nutrition?.saturatedFat}\t" +
                    "Carbohydrates = ${recipe.nutrition?.carbohydrates}\n" +
                    "Number Of Steps: ${recipe.numberOfSteps}\n" +
                    "Steps:\n${recipe.steps}\n" +
                    "Description: ${recipe.description}\n" +
                    "Ingredients:\n${recipe.ingredients}\n" +
                    "Number Of Ingredients: ${recipe.numberOfIngredients}"
        )
    }


    private fun launchSeafoodWithHighProteinUseCase() {
        println("Loading...")
        seafoodWithHighProteinUseCase.getSeafoodWithProteinRecipes()
            .forEachIndexed { index, recipe ->
                println(
                    "${index + 1}. " +
                            "Recipe Name: \n\t${recipe.name} " +
                            "\n\tProtein Amount: \n\t${recipe.nutrition?.protein}"
                )
            }
    }

    private fun presentIraqMeals() {
        println(
            """
            ==================================
            |      Traditional Iraqi Meals    |
            ==================================
           """.trimIndent()
        )
        iraqiMealsUseCase.getIraqiMeals().forEachIndexed { index, recipe ->
            println("${index + 1}. ${recipe.name} - ${recipe.minutes} min - ${recipe.ingredients} ingredients ")
        }
    }


    private fun launchKetoDietUseCase() {
        println("Welcome to Keto Meal Suggester ")
        while (true) {
            println("1. Suggest a Keto Recipe \n2. Go Back ")
            val input: String? = readlnOrNull()
            when (input) {
                "1" -> {
                    suggestRecipeForUser()
                    continue
                }

                "2" -> break
                else -> println("enter a valid number")
            }
        }
    }

    private fun suggestRecipeForUser() {
        val recipe = ketoDiet.suggestKetoRecipe()
        println("Meal name: ${recipe.name}")
        println("Do you want to proceed with Recipe details ? (Y,n) ")
        val input: String? = readlnOrNull()
        if (input == "Y") {
            printRecipeDetails(recipe)
        }
    }

    private fun printRecipeDetails(recipe: Recipe) {
        println("=== Recipe Details ===")
        printBasicInfo(recipe)
        printTags(recipe.tags)
        printNutritionInfo(recipe.nutrition)
        printSteps(recipe.steps, recipe.numberOfSteps)
        printDescription(recipe.description)
        printIngredients(recipe.ingredients, recipe.numberOfIngredients)
    }

    private fun printBasicInfo(recipe: Recipe) {
        println("Name: ${recipe.name ?: "N/A"}")
        println("ID: ${recipe.id ?: "N/A"}")
        println("Preparation Time: ${recipe.minutes ?: "N/A"} minutes")
        println("Contributor ID: ${recipe.contributorId ?: "N/A"}")
        println("Submitted Date: ${recipe.submittedDate ?: "N/A"}\n")
    }

    private fun printTags(tags: List<String>?) {
        println("Tags: ${tags?.joinToString(", ") ?: "None"}\n")
    }

    private fun printNutritionInfo(nutrition: Nutrition?) {
        println("--- Nutrition Information ---")
        nutrition?.let {
            println("Calories: ${it.calories ?: "N/A"}")
            println("Total Fat: ${it.totalFat ?: "N/A"} g")
            println("Sugar: ${it.sugar ?: "N/A"} g")
            println("Sodium: ${it.sodium ?: "N/A"} mg")
            println("Protein: ${it.protein ?: "N/A"} g")
            println("Saturated Fat: ${it.saturatedFat ?: "N/A"} g")
            println("Carbohydrates: ${it.carbohydrates ?: "N/A"} g")
        } ?: println("Nutrition Info: N/A\n")
    }

    private fun printSteps(steps: List<String>?, numberOfSteps: Int?) {
        println("Number of Steps: ${numberOfSteps ?: "N/A"}")
        println("--- Steps ---")
        steps?.forEachIndexed { index, step ->
            println("${index + 1}. $step")
        } ?: println("None")
        println()
    }

    private fun printDescription(description: String?) {
        println("Description: ${description ?: "N/A"}\n")
    }

    private fun printIngredients(ingredients: List<String>?, numberOfIngredients: Int?) {
        println("--- Ingredients ---")
        println("Number of Ingredients: ${numberOfIngredients ?: "N/A"}")
        ingredients?.forEachIndexed { index, ingredient ->
            println("${index + 1}. $ingredient")
        } ?: println("None")
        println()
    }


    private fun launchGuessPrepTimeGame() {
        val recipe = recipeTimeGuessGame.startNewGame()
        println(colors.blue("Guess the preparation time for: ${recipe.name}"))

        var attemptsLeft = 3
        while (attemptsLeft > 0) {
            print("Enter your guess number of minutes: ")
            val guess = readlnOrNull()?.toIntOrNull()
            if (guess == null) {
                println(colors.red("Invalid input. Please enter a valid number of minutes."))
            } else {
                val result = recipeTimeGuessGame.makeGuess(guess, attemptsLeft)
                handleGameFeedback(result)
                if (result is GameFeedback.CorrectGuess || result is GameFeedback.NoAttemptsLeft) {
                    return
                }
                attemptsLeft--
            }
        }

        if (attemptsLeft == 0) {
            println(colors.red("No attempts left. The game is over."))
        }
    }

    private fun handleGameFeedback(result: GameFeedback) {
        when (result) {
            is GameFeedback.CorrectGuess -> {
                println(colors.green("Correct! The preparation time is ${result.actualTime} minutes."))
            }

            is GameFeedback.NoAttemptsLeft -> {
                println(colors.red("No attempts left. The correct time was ${result.actualTime} minutes."))
            }

            is GameFeedback.GuessIsVeryClose -> {
                println(colors.yellow("Very close! Try again. Attempts left: ${result.remainingAttempts}"))
            }

            is GameFeedback.GuessIsWayOff -> {
                println(colors.purple("Way off! Try again. Attempts left: ${result.remainingAttempts}"))
            }

            is GameFeedback.GuessIsNotQuiteRight -> {
                println(colors.cyan("Not quite. Try again. Attempts left: ${result.remainingAttempts}"))
            }

            is GameFeedback.RecipeTimeNotAvailable -> {
                println(colors.red("Error: ${result.errorMessage}"))
            }
        }
    }

    private fun launchItalianGroupMeals() {
        italianGroupMealsUseCase
            .getItalianGroupMeals()
            .forEachIndexed { index, recipe ->
                println("${index + 1}. ${recipe.name} ")

            }
    }

}



