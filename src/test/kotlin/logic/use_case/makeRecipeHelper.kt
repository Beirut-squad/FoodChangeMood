package logic.use_case

import org.example.model.Nutrition
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

fun createRecipe(name: String  , tags: List<String>? = null , description: String? = null , nutrition: Nutrition? = null):Recipe{
    return Recipe(
        name = name,
        id = null,
        minutes = null,
        contributorId = null,
        submittedDate = null,
        tags = tags,
        nutrition = nutrition,
        numberOfSteps = null,
        steps = null,
        description = description,
        ingredients = null,
        numberOfIngredients = null
    )

}

fun createNutrition(
    calories: Float? = null,
    totalFat: Float? = null,
    sugar: Float? = null,
    sodium: Float? = null,
    protein: Float? = null,
    saturatedFat: Float? = null,
    carbohydrates: Float? = null,
): Nutrition {
    return Nutrition(
        calories = calories,
        totalFat = totalFat,
        sugar = sugar,
        sodium = sodium,
        protein = protein,
        saturatedFat = saturatedFat,
        carbohydrates = carbohydrates
    )
}

