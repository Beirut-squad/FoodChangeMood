package ui.features_ui

import org.example.model.Recipe

fun createItalianRecipeHelper(
    name: String?,
    tags: List<String>?
): Recipe {
    return Recipe(
        name = name,
        id = null,
        minutes = null,
        contributorId = null,
        submittedDate = null,
        tags = tags,
        nutrition = null,
        numberOfSteps = null,
        steps = null,
        description = null,
        ingredients = null,
        numberOfIngredients = null
    )

}