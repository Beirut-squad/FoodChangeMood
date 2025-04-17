package org.example.ui


import Utils.checkDateFormat
import org.example.error.NoRecipesFoundForTheGivenDateException
import org.example.error.RecipeNotFoundException
import org.example.logic.Validator
import org.example.logic.use_case.GymHelperUseCase
import org.example.model.Recipe
import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.RandomTenRecipesIncludePotatoUseCase
import org.example.logic.SearchRecipeByDateUseCase
import org.example.model.Recipe
import java.text.ParseException
import java.time.format.DateTimeParseException


class FoodChangeMoodUi(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val searchRecipeByDateUseCase: SearchRecipeByDateUseCase,
    private val gymHelperUseCase: GymHelperUseCase,
    private val validator: Validator,
    private val randomTenRecipesIncludePotatoUseCase: RandomTenRecipesIncludePotatoUseCase
) {
    var isRunning = true
    fun start() {
        showWelcomeMessage()
        presentAvailableFeatures()
    }

    private fun presentAvailableFeatures() {
        while (isRunning) {
            showOptions()
            val input = getUserInput()
            when (input) {
                4 -> launchEasyFoodSuggestionUseCase()
                8 -> launchSearchRecipeByDateUseCase()
                12 -> launchRandomTenPotatoUseCase()
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
        println("4- Easy Food Suggestion ")
        println("8- Search Recipe by add date")
        println("12- I love potato ")
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
                val recipes =  gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(
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

    private fun launchRandomTenPotatoUseCase()
    {
        val potatoMeals= randomTenRecipesIncludePotatoUseCase.findPotatoMeals()
        potatoMeals.forEach {
            println("\t\t $it")
        }
    }


    private fun launchSearchRecipeByDateUseCase(){
        println("Enter the date for which you want to view recipes: example (2006-10-07)")
        try {
            readlnOrNull()?.let { inputDate ->
                inputDate.checkDateFormat()
                searchRecipeByDateUseCase.searchRecipeByDate(inputDate).forEach {idAndName->
                    println("ID = ${idAndName.first} Recipe Name: ${idAndName.second}")
                }
                askUserIfHeWantDetailsOfRecipe()
            }?: println("Please enter a valid date")
        }catch (parseException: ParseException){
            println("Incorrect date format ,Please enter a valid date")
        }catch (dateTimeException:DateTimeParseException){
            println("Incorrect date format ,Please enter a valid date")
        }catch (noRecipesFoundForTheGivenDateException:NoRecipesFoundForTheGivenDateException){
            println(noRecipesFoundForTheGivenDateException.message)
        }
    }


    private fun askUserIfHeWantDetailsOfRecipe(){
        isRunning = false
        println("Do you want to get details of a specific recipe? (Y/N)")
        readlnOrNull()?.lowercase().let {answer->
            when(answer){
                "y" -> { searchRecipeByID() }
                "n" -> { isRunning = true }
                else -> {
                    println("Invalid choice")
                    askUserIfHeWantDetailsOfRecipe()
                }
            }
        }
    }

    private fun searchRecipeByID(){
        try {
            println("Enter the ID of the recipe whose details you want to see:")
            readlnOrNull()?.let {enteredID->
                val idAsNumber = enteredID.toIntOrNull() ?: 0
                if (idAsNumber == 0){
                    println("Enter valid id !")
                    askToBackToMainMenu()
                }else{
                    val recipe = searchRecipeByDateUseCase.viewDetailsOfRecipeByID(enteredID)
                    printRecipe(recipe)
                    isRunning = true
                }
            } ?: { println("Enter valid id !")
                askToBackToMainMenu()
            }
        }catch (e: RecipeNotFoundException){
            // ask to exit from this menu to main menu
            println(e.message)
            askToBackToMainMenu()
        }
    }

    private fun askToBackToMainMenu(){
        println("Are you need to back to main menu ? (Y/N)")
        readlnOrNull()?.lowercase().let {answer->
            when(answer){
                "y" -> { isRunning = true }
                "n" -> { searchRecipeByID() }
                else -> {
                    println("Invalid choice")
                }
            }
        }
    }

    private fun printRecipe(recipe: Recipe){
        println("Recipe Details: ------------------------------------------------\nName: ${recipe.name}\n" +
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
                "Number Of Ingredients: ${recipe.numberOfIngredients}")
    }
}
