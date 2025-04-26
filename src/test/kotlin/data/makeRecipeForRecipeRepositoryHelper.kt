package data

import org.example.model.Recipe

fun makeRecipeForRecipeRepositoryHelper(): Recipe {
    return Recipe(
        name = null,
        id = null,
        minutes = null,
        contributorId = null,
        submittedDate = null,
        tags = null,
        nutrition = null,
        numberOfSteps = null,
        steps = null,
        description = null,
        ingredients = null,
        numberOfIngredients = null
    )
}
