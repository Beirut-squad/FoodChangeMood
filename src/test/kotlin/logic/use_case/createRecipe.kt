package logic.use_case

import org.example.model.Recipe
import org.junit.runner.Description

fun createRecipe(name: String  , tags: List<String>? = null , description: String? = null):Recipe{
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
        description = description,
        ingredients = null,
        numberOfIngredients = null
    )

}