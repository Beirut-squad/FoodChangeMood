package ui.features_ui

import org.example.model.Recipe

fun createRecipeTimeGuessGameHelper(
    name: String?,
): Recipe {
    return Recipe(
        name = name,
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