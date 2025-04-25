package org.example.model

import java.time.LocalDate

data class Recipe(
    val name: String?,
    val id: String?,
    val minutes: Int?,
    val contributorId: String?,
    val submittedDate: LocalDate?,
    val tags: List<String>?,
    val nutrition: Nutrition?,
    val numberOfSteps: Int?,
    val steps: List<String>?,
    val description: String?,
    val ingredients: List<String>?,
    val numberOfIngredients: Int?
)
fun Recipe.isComplete(): Boolean {
    return !ingredients.isNullOrEmpty() &&
            name != null &&
            description != null &&
            !steps.isNullOrEmpty() &&
            minutes != null &&
            !tags.isNullOrEmpty() &&
            nutrition != null &&
            nutrition?.run {
                calories != null && totalFat != null && sugar != null && sodium != null &&
                        protein != null && saturatedFat != null && carbohydrates != null
            } != false
}