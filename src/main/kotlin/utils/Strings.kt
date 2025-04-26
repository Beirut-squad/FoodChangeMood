package org.example.utils

enum class Strings(val message: String) {

    ENTER_COUNTRY_OR_EXIT("Enter a country to explore its meals (or 0 to exit): "),
    INVALID_COUNTRY_NAME_ENTER_LETTERS("Please enter a country name using letters only."),
    INVALID_COUNTRY_NAME("Please enter a valid country name."),
    TRY_ANOTHER_COUNTRY("Try another country."),
    NO_MEALS_FOUND_FOR_COUNTRY("No meals found for '%s'");

    fun formatMessage(country: String): String {
        return when (this) {
            NO_MEALS_FOUND_FOR_COUNTRY -> message.format(country)
            else -> message
        }
    }
}