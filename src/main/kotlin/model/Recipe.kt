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