package org.example.model

import java.util.Date

data class Recipe (
    val name: String?,
    val id: Int?,
    val minutes: Int?,
    val contributorId: Int?,
    val submitted: Date?,
    val tags: List<String>?,
    val nutrition: Nutrition,
    val nStep: Int?,
    val steps: List<String>?,
    val description: String?,
    val ingredients: List<String>?,
    val nIngredients: String?,

    )

