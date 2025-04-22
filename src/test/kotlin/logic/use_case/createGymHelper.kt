package logic.use_case

import org.example.model.Nutrition
import org.example.model.Recipe


fun createGymHelper (
    calories: Float?,
    protein: Float?
)= Recipe (
    name = null,
 id= null,
 minutes= null,
 contributorId= null,
 submittedDate= null,
 tags= null,
 nutrition= createNutritionHelper(calories,protein),
 numberOfSteps= null,
 steps= null,
 description= null,
 ingredients= null,
 numberOfIngredients= null
)


fun createNutritionHelper(
    calories: Float?,
    protein: Float?,
) = Nutrition(
        calories = calories,
        totalFat = null,
        sugar = null,
        sodium = null,
        protein = protein,
        saturatedFat = null,
        carbohydrates = null
    )
