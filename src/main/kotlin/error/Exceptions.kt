package org.example.error

class NoRecipesFoundForTheGivenDateException(message: String): Exception(message)

class IncorrectDateFormat(message: String): Exception(message)

class RecipeNotFoundException(message: String): Exception(message)