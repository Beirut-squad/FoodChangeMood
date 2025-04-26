package ui.features_ui

import org.example.model.Nutrition
import org.example.model.Recipe


fun createSeafoodHelper(
    name: String?, protein: Float?
) = Recipe(
    name = name,
    id = null,
    minutes = null,
    contributorId = null,
    submittedDate = null,
    tags = null,
    nutrition = createNutritionHelper(protein),
    numberOfSteps = null,
    steps = null,
    description = null,
    ingredients = null,
    numberOfIngredients = null
)


fun createNutritionHelper(
    protein: Float?,
) = Nutrition(
    calories = null,
    totalFat = null,
    sugar = null,
    sodium = null,
    protein = protein,
    saturatedFat = null,
    carbohydrates = null
)