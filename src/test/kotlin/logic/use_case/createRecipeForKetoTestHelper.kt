package logic.use_case

import org.example.model.Nutrition
import org.example.model.Recipe

fun createValidRecipe(
    id: String = "1",
    totalFat: Float = 50f,
    saturatedFat: Float = 10f,
    sugar: Float = 2f,
    carbs: Float = 8f
): Recipe {
    return Recipe(
        id = id,
        name = "Valid Recipe",
        nutrition = Nutrition(
            totalFat = totalFat,
            saturatedFat = saturatedFat,
            sugar = sugar,
            carbohydrates = carbs,
            calories = null,
            sodium = null,
            protein = null
        ),
        // Other fields can be default or null
        minutes = null,
        contributorId = null,
        submittedDate = null,
        tags = null,
        numberOfSteps = null,
        steps = null,
        description = null,
        ingredients = null,
        numberOfIngredients = null
    )
}

fun createRecipeWithNutrition(
    totalFat: Float?,
    saturatedFat: Float?,
    sugar: Float?,
    carbs: Float?
): Recipe {
    return createValidRecipe().copy(
        nutrition = Nutrition(
            totalFat = totalFat,
            saturatedFat = saturatedFat,
            sugar = sugar,
            carbohydrates = carbs,
            calories = null,
            sodium = null,
            protein = null
        )
    )
}