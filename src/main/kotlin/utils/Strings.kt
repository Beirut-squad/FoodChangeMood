package org.example.utils

enum class Strings(val message: String) {

    SEARCH_BY_NAME_TITLE("Enter the name of the dish or part of it to search for:"),
    SORRY_COULD_NOT_FIND_RECIPE_MATCHES_NAME("Sorry, we couldn't find a recipe that matches the name you entered."),
    ERROR_OCCURRED_WHILE_SEARCHING("An error occurred while searching: %s"),
    FOUNT_RECIPE("Number of recipes found: %s"),
    ENTER_COUNTRY_OR_EXIT("Enter a country to explore its meals (or 0 to exit): "),
    INVALID_COUNTRY_NAME_ENTER_LETTERS("Please enter a country name using letters only."),
    INVALID_COUNTRY_NAME("Please enter a valid country name."),
    TRY_ANOTHER_COUNTRY("Try another country."),
    NO_MEALS_FOUND_FOR_COUNTRY("No meals found for '%s'");

    fun formatMessage(text: String?): String {
        return when (this) {
            FOUNT_RECIPE -> message.format(text.toString())
            ERROR_OCCURRED_WHILE_SEARCHING -> message.format(text.toString())
            NO_MEALS_FOUND_FOR_COUNTRY -> message.format(text.toString())
            else -> message
        }
    }
}