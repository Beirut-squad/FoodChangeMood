package logic.use_case

import org.example.model.Recipe
import java.time.LocalDate

fun createRecipeHelper(
    name: String? = null,
    id: String? = null,
    minutes: Int? =  null,
    contributorId: String? = null,
    submittedDate: LocalDate? = null,
    tags: List<String>? = null,
    numberOfSteps: Int? = null,
    steps: List<String>? = null,
    description: String? = null,
    ingredients : List<String>?= null,
    numberOfIngredients: Int? = null
): Recipe {
    return Recipe(
        name = name,
        id = id,
        minutes = minutes,
        contributorId = contributorId,
        submittedDate = submittedDate,
        tags = tags,
        nutrition = null,
        numberOfSteps = numberOfSteps,
        steps = steps,
        description = description,
        ingredients = ingredients,
        numberOfIngredients = numberOfIngredients
    )
}
