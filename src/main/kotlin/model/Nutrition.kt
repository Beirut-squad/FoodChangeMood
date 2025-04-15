package org.example.model

import org.example.data.NutritionColumnIndex

data class Nutrition(
    val calories: Float?,
    val totalFat: Float?,
    val sugar: Float?,
    val sodium: Float?,
    val protein: Float?,
    val saturatedFat: Float?,
    val carbohydrates: Float?,
)

fun List<String>.toNutrition(): Nutrition {
    return Nutrition(
        calories = this.getFloatOrNull(NutritionColumnIndex.CALORIES),
        totalFat = this.getFloatOrNull(NutritionColumnIndex.TOTAL_FAT),
        sugar = this.getFloatOrNull(NutritionColumnIndex.SUGAR),
        sodium = this.getFloatOrNull(NutritionColumnIndex.SODIUM),
        protein = this.getFloatOrNull(NutritionColumnIndex.PROTEIN),
        saturatedFat = this.getFloatOrNull(NutritionColumnIndex.SATURATED_FAT),
        carbohydrates = this.getFloatOrNull(NutritionColumnIndex.CARBOHYDRATES)
    )
}

private fun List<String>.getFloatOrNull(index: Int): Float? {
    return if (index in indices) this[index].trim().toFloatOrNull() else null
}