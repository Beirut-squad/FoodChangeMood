package org.example.utils

import org.example.model.Recipe

object RecipeTestData {

        fun createDefaultRecipe(name: String? = null, id: String = "default-id"): Recipe {
            return Recipe(
                name = name,
                id = id,
                minutes = null,
                contributorId = "tester",
                submittedDate = null,
                tags = null,
                nutrition = null,
                numberOfSteps = null,
                steps = null,
                description = null,
                ingredients = null,
                numberOfIngredients = null
            )
        }
    }

