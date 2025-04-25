package ui.features_ui

import org.example.model.Recipe

fun getRecipeForIraqiMealTestsHelper(
    id: String? = null,
    name: String? = null,
    minutes: Int? = null,
    ingredients: List<String>? = null
): Recipe {
    return Recipe(
        name = name,
        id = id,
        minutes = minutes,
        contributorId = null,
        submittedDate = null,
        tags = null,
        nutrition = null,
        numberOfSteps = null,
        steps = null,
        description = null,
        ingredients = ingredients,
        numberOfIngredients = null
    )
}