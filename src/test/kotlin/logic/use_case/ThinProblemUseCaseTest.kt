package logic.use_case

import io.mockk.every
import io.mockk.mockk
import org.example.logic.RecipesRepository
import org.example.logic.use_case.ThinProblemUseCase
import org.example.model.Nutrition
import org.example.model.Recipe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import com.google.common.truth.Truth.assertThat
class ThinProblemUseCaseTest {

 private lateinit var useCase: ThinProblemUseCase
 private lateinit var repository: RecipesRepository

 @BeforeEach
 fun setUp() {
  repository = mockk()
  useCase = ThinProblemUseCase(repository)
 }

 @Test
 fun `GIVEN complete recipes with high calories WHEN findThinProblem called THEN returns one of them`() {
  // Given
  val highRecipe = createCompleteRecipe("Pizza", 800f)
  val lowRecipe = createCompleteRecipe("Salad", 200f)

  every { repository.getAllRecipes() } returns listOf(highRecipe, lowRecipe)

  // When
  val result = useCase.findThinProblem()

  // Then
  assertThat(result).isNotNull()
  assertThat(result!!.nutrition!!.calories!!).isGreaterThan(700f)
 }

 @Test
 fun `GIVEN no recipe has calories above threshold WHEN findThinProblem called THEN returns null`() {
  // Given
  val lowRecipe = createCompleteRecipe("Apple", 300f)
  val lowRecipe2 = createCompleteRecipe("Orange", 150f)

  every { repository.getAllRecipes() } returns listOf(lowRecipe, lowRecipe2)

  // When
  val result = useCase.findThinProblem()

  // Then
  assertThat(result).isNull()
 }

 @Test
 fun `GIVEN only incomplete recipes WHEN findThinProblem called THEN returns null`() {
  // Given
  val incompleteRecipe = createIncompleteRecipe("Burger", 1000f)

  every { repository.getAllRecipes() } returns listOf(incompleteRecipe)

  // When
  val result = useCase.findThinProblem()

  // Then
  assertThat(result).isNull()
 }

 @Test
 fun `GIVEN only incomplete recipes with high calories WHEN findThinProblem called THEN returns null`() {
  // Given
  val recipe1 = createIncompleteRecipe("Pizza", 800f)
  val recipe2 = createIncompleteRecipe("Burger", 900f)

  every { repository.getAllRecipes() } returns listOf(recipe1, recipe2)

  // When
  val result = useCase.findThinProblem()

  // Then
  assertThat(result).isNull()
 }

 @Test
 fun `GIVEN empty recipe list WHEN findThinProblem called THEN returns null`() {
  // Given
  every { repository.getAllRecipes() } returns emptyList()

  // When
  val result = useCase.findThinProblem()

  // Then
  assertThat(result).isNull()
 }

 @Test
 fun `GIVEN recipe with null calories WHEN findThinProblem called THEN should throw NullPointerException`() {
  // Given
  val incompleteRecipe = createIncompleteRecipe_NullCalories("Incomplete Recipe",null)

  every { repository.getAllRecipes() } returns listOf(incompleteRecipe)

  // When & Then
  assertThat(useCase.findThinProblem())

  }
@Test
 fun `GIVEN recipe with nagitive calories`() {
  // Given
  val incompleteRecipe = createIncompleteRecipe_NullCalories("Incomplete Recipe",-1.0f)

  every { repository.getAllRecipes() } returns listOf(incompleteRecipe)

  // When & Then
  assertThat(useCase.findThinProblem())

 }

}



 // Helpers
 private fun createCompleteRecipe(name: String, calories: Float): Recipe {
  return Recipe(
   name = name,
   id = "1",
   minutes = 30,
   contributorId = "100",
   submittedDate = LocalDate.now(),
   tags = listOf("Dinner"),
   nutrition = Nutrition(
    calories = calories,
    totalFat = 10f,
    sugar = 5f,
    sodium = 200f,
    protein = 15f,
    saturatedFat = 3f,
    carbohydrates = 20f
   ),
   numberOfSteps = 2,
   steps = listOf("Step 1", "Step 2"),
   description = "Delicious $name",
   ingredients = listOf("Cheese", "Bread"),
   numberOfIngredients = 2
  )
 }

 private fun createIncompleteRecipe(name: String, calories: Float): Recipe {
  return Recipe(
   name = null, // intentionally incomplete
   id = "2",
   minutes = 30,
   contributorId = "101",
   submittedDate = LocalDate.now(),
   tags = listOf("Lunch"),
   nutrition = Nutrition(
    calories = calories,
    totalFat = 8f,
    sugar = 2f,
    sodium = 150f,
    protein = 10f,
    saturatedFat = 2f,
    carbohydrates = 10f
   ),
   numberOfSteps = null,
   steps = null,
   description = null,
   ingredients = null,
   numberOfIngredients = null
  )
 }

private fun createIncompleteRecipe_NullCalories(name: String, calories: Float?): Recipe {
 return Recipe(
  name = name,
  id = "1",
  minutes = 30,
  contributorId = "100",
  submittedDate = LocalDate.now(),
  tags = listOf("Dinner"),
  nutrition = Nutrition(
   calories = calories,
   totalFat = 10f,
   sugar = 5f,
   sodium = 200f,
   protein = 15f,
   saturatedFat = 3f,
   carbohydrates = 20f
  ),
  numberOfSteps = 2,
  steps = listOf("Step 1", "Step 2"),
  description = "Delicious $name",
  ingredients = listOf("Cheese", "Bread"),
  numberOfIngredients = 2
 )
}
