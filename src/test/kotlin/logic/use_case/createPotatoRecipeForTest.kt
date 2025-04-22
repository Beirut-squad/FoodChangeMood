package logic.use_case

import org.example.model.Nutrition
import org.example.model.Recipe
import kotlin.random.Random

fun createPotatoRecipeForTest(
    name: String = listOf("Bake", "Soup", "Salad", "Pie", "Fries").random(),
    minutes :Int=Random.nextInt(1,250),
    tags :List<String>? =listOf("30-minutes-or-less", "easy", "vegetarian", "gluten-free"),
    nutrition :Nutrition? =generateRandomNutrition(),
    description: String? ="A delicious and quick potato dish.",
    steps :List<String>?=listOf(  "Preheat the oven to 375°F.",
        "Chop the potatoes into bite-sized pieces.",
        "Season with salt, pepper, and your favorite spices."),
    ingredients: List<String>? = listOf( "cheese", "garlic", "bacon", "herbs", "olive oil", "spinach","tomato",
        "bell pepper","Potato", "potato","potatO", "POTATO").shuffled().take(3)
)
: Recipe {
    return Recipe(
        name = name,
        id = null,
        minutes = minutes,
        contributorId = null,
        submittedDate = null,
        tags = tags,
        nutrition = nutrition,
        numberOfSteps = null,
        steps = steps,
        description = description,
        ingredients = ingredients,
        numberOfIngredients = null
    )
}

fun generateRandomNutrition(): Nutrition {
    return Nutrition(
        calories = Random.nextFloat() * 500,
        totalFat = Random.nextFloat() * 50,
        sugar = Random.nextFloat() * 30,
        sodium = Random.nextFloat() * 2000,
        protein = Random.nextFloat() * 40,
        saturatedFat = Random.nextFloat() * 20,
        carbohydrates = Random.nextFloat() * 100
    )

}
