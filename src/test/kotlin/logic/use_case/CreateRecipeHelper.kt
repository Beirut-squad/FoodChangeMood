package logic.use_case

import org.example.model.Recipe


fun createEasyRecipeHelper(
    name: String?,
    minutes: Int?,
    ingredients: List<String>?,
    steps: List<String>?
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
        steps = steps,
        description = null,
        ingredients = ingredients,
        numberOfIngredients = null
    )
}
