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