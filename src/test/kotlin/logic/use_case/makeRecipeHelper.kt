package logic.use_case

import org.example.model.Recipe

fun makeRecipeHelper(
    name: String?,
    minutes: Int?
): Recipe {
    return Recipe(
        name = name,
        id = null,
        minutes = minutes,
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