package org.example.model

import org.example.data.NutritionColumnIndex

data class Nutrition(
    val calories: String?,
    val totalFat: String?,
    val sugar: String?,
    val sodium: String?,
    val protein: String?,
    val saturatedFat: String?,
    val carbohydrates: String?,
)

fun List<String>.toNutrition(): Nutrition {
    return Nutrition(
        calories = this.getOrNull(NutritionColumnIndex.CALORIES),
        totalFat = this.getOrNull(NutritionColumnIndex.TOTAL_FAT),
        sugar = this.getOrNull(NutritionColumnIndex.SUGAR),
        sodium = this.getOrNull(NutritionColumnIndex.SODIUM),
        protein = this.getOrNull(NutritionColumnIndex.PROTEIN),
        saturatedFat = this.getOrNull(NutritionColumnIndex.SATURATED_FAT),
        carbohydrates = this.getOrNull(NutritionColumnIndex.CARBOHYDRATES)
    )
}