package logic.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.RecipesRepository
import org.example.logic.use_case.RandomTenRecipesIncludePotatoUseCase
import org.example.model.Nutrition
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class RandomTenRecipesIncludePotatoUseCaseTest {

 private val recipesRepository: RecipesRepository = mockk(relaxed = true)
 private lateinit var randomTenRecipesIncludePotatoUseCase: RandomTenRecipesIncludePotatoUseCase

 @BeforeEach
 fun setup() {

      randomTenRecipesIncludePotatoUseCase = RandomTenRecipesIncludePotatoUseCase(recipesRepository)
 }

 @Test
 fun `should return recipes with potato in any case, case-insensitive`() {
  // Given

  every { recipesRepository.getAllRecipes() } returns List(15) {
   createPotatoRecipeForTest()
  }

  // When
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()

  // Then
  result.forEach { recipe ->
   assertThat(recipe.ingredients?.any { ingredient ->
    ingredient.contains("potato", ignoreCase = true)
   }).isTrue()
  }
 }

 @Test
 fun `should return empty list if ingredients contain potato but recipe is incomplete`() {
  // Given

  every { recipesRepository.getAllRecipes() } returns List(15) {
   createPotatoRecipeForTest(
    description = null
   )
  }

  // When
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()

  // Then
  assertThat(result).isEmpty()
 }

 @Test
 fun `should return 10 potato recipes when more than 10 available`() {
  // Given

 every { recipesRepository.getAllRecipes() } returns List(50) {
   createPotatoRecipeForTest(
    ingredients = listOf( "cheese", "garlic", "potato","bacon", "butter").shuffled().take(3)
   )
  }
  // when
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()
  // Then
  assertThat(result).hasSize(10)
 }

 @Test
 fun `should return only potato recipes`() {
  // Given
  every { recipesRepository.getAllRecipes() } returns List(25) {
   createPotatoRecipeForTest()
  }

  // When
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()

  // Then
  result.forEach { recipe ->
   assertThat(recipe.ingredients.orEmpty().any { it.contains("potato", ignoreCase = true) }).isTrue()
  }
 }

 @Test
 fun `should return empty list when no potato recipes available`() {
  // Given

  val additionalIngredients = listOf(
   "cheese", "garlic", "bacon", "chicken", "butter", "cream", "herbs", "olive oil",
   "spinach", "carrot", "tomato", "peas", "bell pepper"
  )
  every { recipesRepository.getAllRecipes() } returns List(15) {
   createPotatoRecipeForTest(
    ingredients = additionalIngredients.shuffled().take((2..5).random())
   )
  }

  // When
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()

  // Then
  assertThat(result).isEmpty()
 }


 @Test
 fun `should return empty list when no recipes available in the repository`() {
  // Given
  every { recipesRepository.getAllRecipes() } returns emptyList()

  // When
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()

  // Then
  assertThat(result).isEmpty()
 }

 @Test
 fun `should return empty list when recipes have no ingredients`() {
  // Given

  every { recipesRepository.getAllRecipes() } returns List(15) {
   createPotatoRecipeForTest(
    ingredients = emptyList()
   )
  }

  // When
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()

  // Then
  assertThat(result).isEmpty()
 }

 @Test
 fun `should return empty list when recipe has no steps`() {
  // Given
  every { recipesRepository.getAllRecipes() } returns List(25) {
   createPotatoRecipeForTest(
    steps = emptyList()
   )
  }
  // When
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()

  // Then
  assertThat(result).isEmpty()
 }

 @Test
 fun `should return all available potato recipes if less than 10 available`() {
  // Given
  every { recipesRepository.getAllRecipes() } returns List(5) {
   createPotatoRecipeForTest(
    ingredients = listOf( "potato","bacon", "chicken")
   )
  }

  // When
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()

  // Then
  assertThat(result).hasSize(5)
 }

 @Test
 fun `should return empty list when recipe has no tags`() {
  // Given
  every { recipesRepository.getAllRecipes() } returns List(25) {
   createPotatoRecipeForTest(
    tags = null
   )
  }
  // When
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()

  // Then
  assertThat(result).isEmpty()
 }

 @Test
 fun `should return empty list when nutrition has null values`() {
  // Given
  every { recipesRepository.getAllRecipes() } returns List(15) {
   createPotatoRecipeForTest(
    nutrition = Nutrition(
     calories = 200f,
     totalFat = null,
     sugar = 5f,
     sodium = 100f,
     protein = 10f,
     saturatedFat = 1f,
     carbohydrates = 20f
    )
   )
  }

  // When
  val result = randomTenRecipesIncludePotatoUseCase.findPotatoMeals()

  // Then
  assertThat(result).isEmpty()
 }


}
