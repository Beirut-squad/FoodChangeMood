package org.example.model

import java.util.Date

data class Recipe (
    val name: String?,
    val id: String?,
    val minutes: Int?,
    val contributorId: String?,
    val submittedDate: Date?,
    val tags: List<String>?,
    val nutrition: Nutrition?,
    val numberOfSteps: Int?,
    val steps: List<String>?,
    val description: String?,
    val ingredients: List<String>?,
    val numberOfIngredients: Int?
)
 fun Recipe.isComplete(): Boolean {
    return ingredients != null &&
            name != null &&
            nutrition != null &&
            description != null &&
            steps != null &&
            contributorId != null &&
            id != null &&
            minutes != null &&
            numberOfIngredients != null &&
            numberOfSteps != null &&
            submittedDate != null &&
            tags != null
}