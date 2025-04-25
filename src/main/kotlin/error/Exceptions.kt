package org.example.error

class NoRecipesFoundForTheGivenDateException(message: String): Exception(message)

class RecipeNotFoundException(message: String): Exception(message)

class ThereIsNoNameException(message: String): Exception(message)

class FileDoesNotExistException: Exception()