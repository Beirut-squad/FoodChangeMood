package ui.features_ui

import io.mockk.*
import org.example.logic.use_case.RandomTenRecipesIncludePotatoUseCase
import org.example.model.Nutrition
import org.example.model.Recipe
import org.example.ui.RecipeFormatter
import org.example.ui.Viewer
import org.example.ui.features_ui.RandomTopTenRecipesIncludePotatoUi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RandomTopTenRecipesIncludePotatoUiTest{

 private lateinit var viewer: Viewer
 private lateinit var randomTenRecipesIncludePotatoUseCase:RandomTenRecipesIncludePotatoUseCase
 private lateinit var randomTopTenRecipesIncludePotatoUi:RandomTopTenRecipesIncludePotatoUi

@BeforeEach
fun setup(){
 randomTenRecipesIncludePotatoUseCase = mockk(relaxed = true)
 viewer = mockk(relaxed = true)
 randomTopTenRecipesIncludePotatoUi = RandomTopTenRecipesIncludePotatoUi(randomTenRecipesIncludePotatoUseCase, viewer)
 mockkStatic(RecipeFormatter::class)
}
 @Test
 fun `should call findPotatoMeals fun form randomTenRecipesIncludePotatoUseCase when show fun is called`() {
  randomTopTenRecipesIncludePotatoUi.show()

  verify { randomTenRecipesIncludePotatoUseCase.findPotatoMeals() }
 }

 @Test
 fun `should display error message when no potato recipes are found`(){
  //Given
  every { randomTenRecipesIncludePotatoUseCase.findPotatoMeals() } returns emptyList()
  //When
  randomTopTenRecipesIncludePotatoUi.show()

  verify { viewer.printError("Recipe not found") }
 }

}

