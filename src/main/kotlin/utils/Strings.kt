package org.example.utils

enum class Strings(val message: String) {

    SEARCH_BY_NAME_TITLE("Enter the name of the dish or part of it to search for:"),
    SORRY_COULD_NOT_FIND_RECIPE_MATCHES_NAME("Sorry, we couldn't find a recipe that matches the name you entered."),
    ERROR_OCCURRED_WHILE_SEARCHING("An error occurred while searching: %s"),
    FOUNT_RECIPE("Found the recipe: %s");

    fun formatMessage(text: String?): String {
        return when (this) {
            FOUNT_RECIPE -> message.format(text.toString())
            ERROR_OCCURRED_WHILE_SEARCHING -> message.format(text.toString())
            else -> message
        }
    }
}