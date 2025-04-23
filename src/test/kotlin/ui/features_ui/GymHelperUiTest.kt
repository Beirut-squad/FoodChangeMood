package ui.features_ui

import io.mockk.*
import org.example.logic.Validator
import org.example.logic.use_case.GymHelperUseCase
import org.example.ui.Reader
import org.example.ui.Viewer
import org.example.ui.features_ui.GymHelperUi
import org.example.utils.Colors
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class GymHelperUiTest {
 private val gymHelperUseCase: GymHelperUseCase = mockk()
 private val validator: Validator = mockk(relaxed = true)
 private val colors: Colors = mockk(relaxed = true)
 private val viewer: Viewer = mockk(relaxed = true)
 private val reader: Reader = mockk(relaxed = true)
 private lateinit var gymHelperUi: GymHelperUi

 @BeforeEach
 fun setup() {
     gymHelperUi = GymHelperUi(
      gymHelperUseCase = gymHelperUseCase,
      validator = validator,
      colors = colors,
      viewer = viewer,
      reader = reader

  )

 }


 @Test
 fun `should print intro message when show function is called`() {
  // Given
  every { reader.readInput() } returnsMany listOf("5", "6")
  every { validator.validateGymHelperInput("6","5") } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(any(), any()) } returns emptyList()

  // When
  gymHelperUi.show()

  // Then
  verify {
   viewer.printOutputWithNewLine(
    colors.cyan("Gym helper: Get meals that match the protein and calories amounts you choose or close to them.")
   )
  }
 }

 @Test
 fun `should prompt for protein and calories input`() {
  // Given
  every { reader.readInput() } returnsMany listOf("50", "2000")
  every { validator.validateGymHelperInput(any(), any()) } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(any(), any()) } returns emptyList()


  // When
  gymHelperUi.show()

  // Then
  verify {
   viewer.printOutput(colors.blue("Enter the amount of protein: "))
   viewer.printOutput(colors.blue("Enter the amount of calories: "))
  }

  verify {
   reader.readInput()
   reader.readInput()
  }
 }


 @Test
 fun `should display error message when input is invalid`() {
  // Given
  every { reader.readInput() } returnsMany listOf("invalid", "invalid", "100", "2000")
  every { validator.validateGymHelperInput("invalid", "invalid") } returns false
  every { validator.validateGymHelperInput("2000", "100") } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(any(), any()) } returns emptyList()

  // When
  gymHelperUi.show()

  // Then
  verify {
   viewer.printOutputWithNewLine(colors.red("Invalid input."))
  }
 }


 @Test
 fun `should call use case with correct parameters when input is valid`() {
  // Given
  every { reader.readInput() } returnsMany listOf("100", "2000")
  every { validator.validateGymHelperInput("2000", "100") } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(2000f, 100f) } returns emptyList()

  // When
  gymHelperUi.show()

  // Then
  verify {
   gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(2000f, 100f)
  }
 }

 @Test
 fun `should handle null input with default values`() {
  // Given
  every { reader.readInput() } returnsMany listOf(null, null)
  every { validator.validateGymHelperInput(null, null) } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(0f, 0f) } returns emptyList()

  // When
  gymHelperUi.show()

  // Then
  verify {
   gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(0f, 0f)
  }
 }


 @Test
 fun `should use nullable values from nutrition correctly`() {
  // Given
  val recipe = createRecipeForGymHelper(
   name = "Test Recipe",
   nutrition = null,  // Null nutrition
   ingredients = listOf("Ingredient 1"),
   steps = listOf("Step 1")
  )

  every { reader.readInput() } returnsMany listOf("100", "2000")
  every { validator.validateGymHelperInput(any(), any()) } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(any(), any()) } returns listOf(recipe)
  every { colors.green(any()) } answers { firstArg() }

  // When
  gymHelperUi.show()

  // Then
  verify {
   viewer.printOutputWithNewLine("Calories: 0.0, Protein: 0.0")
  }
 }

 @Test
 fun `should use values from nutrition correctly`() {
  // Given
  val recipe = createRecipeForGymHelper(
   name = "Test Recipe",
   nutrition = createNutritionForGymHelper(calories = 300.0f, protein = 25.0f),  // Null nutrition
   ingredients = listOf("Ingredient 1"),
   steps = listOf("Step 1")
  )

  every { reader.readInput() } returnsMany listOf("100", "2000")
  every { validator.validateGymHelperInput(any(), any()) } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(any(), any()) } returns listOf(recipe)

  // When
  gymHelperUi.show()

  // Then
  verify {
   viewer.printOutputWithNewLine( colors.green("Calories: 300.0, Protein: 25.0"))
  }
 }

 @Test
 fun `should display recipes returned by use case`() {
  // Given
  val recipes = listOf(
   createRecipeForGymHelper(
    name = "Protein Shake",
    nutrition = createNutritionForGymHelper(calories = 300.0f, protein = 25.0f),
    ingredients = listOf("Milk", "Protein powder", "Banana"),
    steps = listOf("Mix everything in a blender", "Serve cold")
   ),
   createRecipeForGymHelper(
    name = "Chicken Salad",
    nutrition = createNutritionForGymHelper(calories = 450.0f, protein = 35.0f),
    ingredients = listOf("Chicken breast", "Lettuce", "Tomatoes", "Cucumber"),
    steps = listOf("Cook chicken", "Mix vegetables", "Add chicken to salad", "Serve")
   )
  )
  every { reader.readInput() } returnsMany listOf("100", "2000")
  every { validator.validateGymHelperInput("2000", "100") } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(2000f, 100f) } returns recipes
  every { colors.green(any()) } answers { firstArg() }

  // When
  gymHelperUi.show()

  // Then
  verify {

   viewer.printOutputWithNewLine("Meal 1: Protein Shake")
   viewer.printOutputWithNewLine("Calories: 300.0, Protein: 25.0")
   viewer.printOutput("Ingredients: ")
   viewer.printOutput("Milk, ")
   viewer.printOutput("Protein powder, ")
   viewer.printOutput("Banana, ")
   viewer.printOutputWithNewLine("How to Make: ")
   viewer.printOutput("Step 1: ")
   viewer.printOutputWithNewLine("Mix everything in a blender")
   viewer.printOutput("Step 2: ")
   viewer.printOutputWithNewLine("Serve cold")
   viewer.printOutputWithNewLine("")

   viewer.printOutputWithNewLine("Meal 2: Chicken Salad")
   viewer.printOutputWithNewLine("Calories: 450.0, Protein: 35.0")
   viewer.printOutput("Ingredients: ")
   viewer.printOutput("Chicken breast, ")
   viewer.printOutput("Lettuce, ")
   viewer.printOutput("Tomatoes, ")
   viewer.printOutput("Cucumber, ")
   viewer.printOutputWithNewLine("How to Make: ")
   viewer.printOutput("Step 1: ")
   viewer.printOutputWithNewLine("Cook chicken")
   viewer.printOutput("Step 2: ")
   viewer.printOutputWithNewLine("Mix vegetables")
   viewer.printOutput("Step 3: ")
   viewer.printOutputWithNewLine("Add chicken to salad")
   viewer.printOutput("Step 4: ")
   viewer.printOutputWithNewLine("Serve")
   viewer.printOutputWithNewLine("")
  }
 }


 @Test
 fun `should reject zero values for protein and calories`() {
  // Given
  every { reader.readInput() } returnsMany listOf("0", "2000", "100", "0", "100", "2000")
  every { validator.validateGymHelperInput("2000", "0") } returns false
  every { validator.validateGymHelperInput("0", "100") } returns false
  every { validator.validateGymHelperInput("2000", "100") } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(any(), any()) } returns emptyList()

  // When
  gymHelperUi.show()

  // Then
  verify {
   viewer.printOutputWithNewLine(colors.red("Invalid input."))
  }

 }

 @Test
 fun `should handle recipes with null fields`() {
  // Given
  val recipes = listOf(
   createRecipeForGymHelper(
    name = "Basic Recipe",
    nutrition = null,
    ingredients = null,
    steps = null
   )
  )

  every { reader.readInput() } returnsMany listOf("100", "2000")
  every { validator.validateGymHelperInput("2000", "100") } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(2000f, 100f) } returns recipes
  every { colors.green(any()) } answers { firstArg() }

  // When
  gymHelperUi.show()

  // Then
  verify {
   viewer.printOutputWithNewLine("Meal 1: Basic Recipe")
   viewer.printOutputWithNewLine("Calories: 0.0, Protein: 0.0")
   viewer.printOutputWithNewLine("")
  }

  verify(exactly = 0) {
   viewer.printOutput("Ingredients: ")
   viewer.printOutputWithNewLine("How to Make: ")
  }
 }







 @Test
 fun `should continue prompting until valid input is received`() {
  // Given
  every { reader.readInput() } returnsMany listOf("invalid", "200", "invalid", "2500", "100", "2000")
  every { validator.validateGymHelperInput("200", "invalid") } returns false
  every { validator.validateGymHelperInput("2500", "invalid") } returns false
  every { validator.validateGymHelperInput("2000", "100") } returns true
  every { gymHelperUseCase.getRecipesMatchOrApproximateAmountOfCaloriesAndProtein(2000f, 100f) } returns emptyList()

  // When
  gymHelperUi.show()

  // Then
  verify {

   viewer.printOutput(colors.blue("Enter the amount of protein: "))
   reader.readInput()
   viewer.printOutput(colors.blue("Enter the amount of calories: "))
   reader.readInput()
   viewer.printOutputWithNewLine(colors.red("Invalid input."))

   viewer.printOutput(colors.blue("Enter the amount of protein: "))
   reader.readInput()
   viewer.printOutput(colors.blue("Enter the amount of calories: "))
   reader.readInput()
   viewer.printOutputWithNewLine(colors.red("Invalid input."))

   viewer.printOutput(colors.blue("Enter the amount of protein: "))
   reader.readInput()
   viewer.printOutput(colors.blue("Enter the amount of calories: "))
   reader.readInput()
  }
 }





}