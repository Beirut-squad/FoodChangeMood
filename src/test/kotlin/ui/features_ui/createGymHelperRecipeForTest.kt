package ui.features_ui

import org.example.model.Nutrition
import org.example.model.Recipe

fun createRecipeForGymHelper(
    name: String?,
    nutrition: Nutrition?,
    ingredients: List<String>?,
    steps: List<String>?
): Recipe {
    return Recipe(
        name = name,
        id = null,
        minutes = null,
        contributorId = null,
        submittedDate = null,
        tags = null,
        nutrition = nutrition,
        numberOfSteps = steps?.size ?: 0,
        steps = steps,
        description = null,
        ingredients = ingredients,
        numberOfIngredients = ingredients?.size ?: 0
    )
}

fun createNutritionForGymHelper(
    calories: Float?,
    protein: Float?,
    totalFat: Float = 0f,
    sugar: Float = 0f,
    sodium: Float = 0f,
    saturatedFat: Float = 0f,
    carbohydrates: Float = 0f
): Nutrition {
    return Nutrition(
        calories = calories,
        protein = protein,
        totalFat = totalFat,
        sugar = sugar,
        sodium = sodium,
        saturatedFat = saturatedFat,
        carbohydrates = carbohydrates
    )

}