package org.example.model

import java.time.LocalDate

data class Recipe(
    val name: String? = null,
    val id: String? = null,
    val minutes: Int? = null,
    val contributorId: String? = null,
    val submittedDate: LocalDate? = null,
    val tags: List<String>? = null,
    val nutrition: Nutrition? = null,
    val numberOfSteps: Int? = null,
    val steps: List<String>? = null,
    val description: String? = null,
    val ingredients: List<String>? = null,
    val numberOfIngredients: Int? = null
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