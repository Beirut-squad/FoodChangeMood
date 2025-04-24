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
    return ingredients != null &&
            name != null &&
            nutrition != null &&
            description != null &&
            steps != null &&
            minutes != null &&
            tags != null
}