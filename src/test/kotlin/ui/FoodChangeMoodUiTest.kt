package ui

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.example.ui.FoodChangeMoodUi
import org.example.ui.Reader
import org.example.ui.Viewer
import org.example.ui.features_ui.EasyFoodSuggestionUI
import org.example.ui.features_ui.GlobalFoodCultureUI
import org.example.ui.features_ui.GymHelperUi
import org.example.ui.features_ui.HealthyFoodRecipesUi
import org.example.ui.features_ui.IngredientsGuessingGameUi
import org.example.ui.features_ui.IraqiMealsUi
import org.example.ui.features_ui.ItalianGroupMealsUi
import org.example.ui.features_ui.KetoDietUi
import org.example.ui.features_ui.RandomTopTenRecipesIncludePotatoUi
import org.example.ui.features_ui.RecipesTimeGuessGameUi
import org.example.ui.features_ui.SeafoodWithHighProteinUi
import org.example.ui.features_ui.SearchByNameUI
import org.example.ui.features_ui.SearchRecipeByDateUi
import org.example.ui.features_ui.SweetWithoutEggsUi
import org.example.ui.features_ui.ThinProblemUi
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test


class FoodChangeMoodUiTest {

    private val iraqiMealsUi: IraqiMealsUi = mockk(relaxed = true)
    private val searchByNameUI: SearchByNameUI = mockk(relaxed = true)
    private val easyFoodSuggestionUI: EasyFoodSuggestionUI = mockk(relaxed = true)
    private val searchRecipeByDateUi: SearchRecipeByDateUi = mockk(relaxed = true)
    private val sweetWithoutEggsUi: SweetWithoutEggsUi = mockk(relaxed = true)
    private val thinProblemUi: ThinProblemUi = mockk(relaxed = true)
    private val ketoDietUi: KetoDietUi = mockk(relaxed = true)
    private val gymHelperUi: GymHelperUi = mockk(relaxed = true)
    private val healthyFoodRecipesUi: HealthyFoodRecipesUi = mockk(relaxed = true)
    private val seafoodWithHighProteinUi: SeafoodWithHighProteinUi = mockk(relaxed = true)
    private val randomTopTenRecipesIncludePotatoUi: RandomTopTenRecipesIncludePotatoUi = mockk(relaxed = true)
    private val italianGroupMealsUi: ItalianGroupMealsUi = mockk(relaxed = true)
    private val recipesTimeGuessGameUi: RecipesTimeGuessGameUi = mockk(relaxed = true)
    private val ingredientsGuessingGameUi: IngredientsGuessingGameUi = mockk(relaxed = true)
    private val globalFoodCultureUI: GlobalFoodCultureUI = mockk(relaxed = true)
    private val viewer: Viewer = mockk(relaxed = true)
    private val reader: Reader = mockk(relaxed = true)
    private lateinit var foodChangeMoodUi: FoodChangeMoodUi

    @BeforeEach
    fun setup() {
        foodChangeMoodUi = FoodChangeMoodUi(
            iraqiMealsUi,
            searchByNameUI,
            easyFoodSuggestionUI,
            searchRecipeByDateUi,
            sweetWithoutEggsUi,
            thinProblemUi,
            ketoDietUi,
            gymHelperUi,
            healthyFoodRecipesUi,
            seafoodWithHighProteinUi,
            randomTopTenRecipesIncludePotatoUi,
            italianGroupMealsUi,
            recipesTimeGuessGameUi,
            ingredientsGuessingGameUi,
            globalFoodCultureUI,
            viewer,
            reader
        )
    }

    @Test
    fun `should show welcome message and options in order when show function is called`() {
        //given
        every { reader.readInput() } returns "0"
        foodChangeMoodUi.start()
        verifyOrder {
            viewer.printTitle("Welcome to Food Change Mood App")
            viewer.printOption("\n=== Please enter the number of the service you want: ")
            viewer.printOption("1- Get Quick and Healthy Meals")
            viewer.printOption("2- Smart Meal Search (By name) ")
            viewer.printOption("3- Iraq Food")
            viewer.printOption("4- Easy Food Suggestion")
            viewer.printOption("5- Time Guess Game")
            viewer.printOption("6- Sweets with no eggs")
            viewer.printOption("7- Keto Diet Food Suggestion ")
            viewer.printOption("8- Search Recipe by add date")
            viewer.printOption("9- Gym Helper")
            viewer.printOption("10- Global Food Culture")
            viewer.printOption("11- Ingredients Guessing Game")
            viewer.printOption("12- I love potato ")
            viewer.printOption("13- Thin problem Suggestion ")
            viewer.printOption("14- Seafood with High Protein ")
            viewer.printOption("15- Italian Group Meals ")
            viewer.printExitOption("0- Enter 0 to exit the app")
        }
    }

    @Test
    fun `should call show healthyFoodRecipesUi when when start function is called with option 1`() {
        every { reader.readInput() } returnsMany listOf("1", "0")
        foodChangeMoodUi.start()
        verify {
            healthyFoodRecipesUi.show()
        }
    }

    @Test
    fun `should call searchByNameUI when start function is called with option 2`() {
        every { reader.readInput() } returnsMany listOf("2", "0")
        foodChangeMoodUi.start()
        verify {
            searchByNameUI.show()
        }
    }

    @Test
    fun `should call iraqiMealsUi when start function is called with option 3`() {
        every { reader.readInput() } returnsMany listOf("3", "0")
        foodChangeMoodUi.start()
        verify {
            iraqiMealsUi.show()
        }
    }

    @Test
    fun `should call easyFoodSuggestionUI when start function is called with option 4`() {
        every { reader.readInput() } returnsMany listOf("4", "0")
        foodChangeMoodUi.start()
        verify {
            easyFoodSuggestionUI.show()
        }
    }

    @Test
    fun `should call recipesTimeGuessGameUi when start function is called with option 5`() {
        every { reader.readInput() } returnsMany listOf("5", "0")
        foodChangeMoodUi.start()
        verify {
            recipesTimeGuessGameUi.show()
        }
    }

    @Test
    fun `should call sweetWithoutEggsUi when start function is called with option 6`() {
        every { reader.readInput() } returnsMany listOf("6", "0")
        foodChangeMoodUi.start()
        verify {
            sweetWithoutEggsUi.show()
        }
    }

    @Test
    fun `should call ketoDietUi when start function is called with option 7`() {
        every { reader.readInput() } returnsMany listOf("7", "0")
        foodChangeMoodUi.start()
        verify {
            ketoDietUi.show()
        }
    }

    @Test
    fun `should call searchRecipeByDateUi when start function is called with option 8`() {
        every { reader.readInput() } returnsMany listOf("8", "0")
        foodChangeMoodUi.start()
        verify {
            searchRecipeByDateUi.show()
        }
    }

    @Test
    fun `should call gymHelperUi when start function is called with option 9`() {
        every { reader.readInput() } returnsMany listOf("9", "0")
        foodChangeMoodUi.start()
        verify {
            gymHelperUi.show()
        }
    }

    @Test
    fun `should call globalFoodCultureUI when start function is called with option 10`() {
        every { reader.readInput() } returnsMany listOf("10", "0")
        foodChangeMoodUi.start()
        verify {
            globalFoodCultureUI.show()
        }
    }

    @Test
    fun `should call ingredientsGuessingGameUi when start function is called with option 11`() {
        every { reader.readInput() } returnsMany listOf("11", "0")
        foodChangeMoodUi.start()
        verify {
            ingredientsGuessingGameUi.show()
        }
    }

    @Test
    fun `should call randomTopTenRecipesIncludePotatoUi when start function is called with option 12`() {
        every { reader.readInput() } returnsMany listOf("12", "0")
        foodChangeMoodUi.start()
        verify {
            randomTopTenRecipesIncludePotatoUi.show()
        }
    }

    @Test
    fun `should call thinProblemUi when start function is called with option 13`() {
        every { reader.readInput() } returnsMany listOf("13", "0")
        foodChangeMoodUi.start()
        verify {
            thinProblemUi.show()
        }
    }

    @Test
    fun `should call seafoodWithHighProteinUi when start function is called with option 14`() {
        every { reader.readInput() } returnsMany listOf("14", "0")
        foodChangeMoodUi.start()
        verify {
            seafoodWithHighProteinUi.show()
        }
    }

    @Test
    fun `should call italianGroupMealsUi when start function is called with option 15`() {
        every { reader.readInput() } returnsMany listOf("15", "0")
        foodChangeMoodUi.start()
        verify {
            italianGroupMealsUi.show()
        }
    }

    @Test
    fun `should exit the menu and show goodbye message when entering option 0`() {
        every { reader.readInput() } returns "0"
        foodChangeMoodUi.start()
        verify(exactly = 1) { viewer.printGoodbyeMessage("Goodbye :)") }
    }

    @Test
    fun `should print error message when entering invalid input`() {
        every { reader.readInput() } returnsMany listOf("hello", "0")
        foodChangeMoodUi.start()
    }
}