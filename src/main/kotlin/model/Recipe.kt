package org.example.model

import java.util.Date

data class Recipe (
    val name: String?,
    val id: String?,
    val minutes: String?,
    val contributorId: String?,
    val submittedDate: String?,
    val tags: List<String>?,
    val nutrition: Nutrition,
    val numberOfSteps: String?,
    val steps: List<String>?,
    val description: String?,
    val ingredients: List<String>?,
    val numberOfIngredients: String?
)