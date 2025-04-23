package logic.use_case

import org.example.model.Nutrition
import org.example.model.Recipe
import java.time.LocalDate

fun createRecipeForHealthyRecipes(
    name: String?,
    minutes: Int?,
    totalFat: Float?,
    saturatedFat: Float?,
    carbohydrates: Float?
): Recipe {
    return Recipe(
        name = name, id = null, minutes = minutes, null, null, null,
        Nutrition(
            null, totalFat = totalFat, null, null, null, saturatedFat = saturatedFat,
            carbohydrates = carbohydrates
        ), null, null, null, null, null
    )
}

fun createRecipeForSweetWithNoEgg(
    name: String?,
    description: String?,
    minutes: Int? = 10,
    ingredients: List<String>? = listOf("","",""),
    nutrition: Nutrition? = Nutrition(90.0f, 10.0f,8.0f,5.0f,1.0f,22.0f,30.0f),
    steps: List<String>? = listOf("","",""),
    tags : List<String>? = listOf("","","")
    ): Recipe {
    return Recipe(
        name = name,
        description = description,
        ingredients = ingredients,
        nutrition = nutrition,
        steps = steps,
        minutes = minutes,
        tags = tags,
        id = null,
        contributorId = null,
        submittedDate = null,
        numberOfSteps = null,
        numberOfIngredients = null
    )
}

