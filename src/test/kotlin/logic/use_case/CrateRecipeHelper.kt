package logic.use_case

import org.example.model.Nutrition
import org.example.model.Recipe

fun createRecipeForHealthyRecipes(
    name: String?,
    minutes: Int?,
    totalFat: Float?,
    saturatedFat: Float?,
    carbohydrates: Float?,
    nutrition: Nutrition? = Nutrition(
        null, totalFat = totalFat, null, null, null, saturatedFat = saturatedFat,
        carbohydrates = carbohydrates
    )
): Recipe {
    return Recipe(
        name = name, id = null, minutes = minutes, null, null, null,
        nutrition = nutrition, null, null, null, null, null
    )
}