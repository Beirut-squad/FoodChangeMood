import org.example.utils.Colors

sealed class RecipeTimeGameResult(val messageResult: String) {
    companion object {
        private val colors = Colors()
    }
    class CorrectGuess(actualTime: Int) : RecipeTimeGameResult(
        messageResult = colors.green("Correct! The preparation time is $actualTime minutes.")
    )
    class NoAttemptsLeft(actualTime: Int) : RecipeTimeGameResult(
        messageResult = colors.red("No attempts left. The correct time was $actualTime minutes.")
    )
    class GuessIsVeryClose(remainingAttempts: Int) : RecipeTimeGameResult(
        messageResult =colors.blue("Very close! Try again. Attempts left: $remainingAttempts")
    )
    class GuessIsWayOff(remainingAttempts: Int) : RecipeTimeGameResult(
        messageResult = colors.purple("Way off! Try again. Attempts left: $remainingAttempts")
    )
    class GuessIsNotQuiteRight(remainingAttempts: Int) : RecipeTimeGameResult(
        messageResult = colors.cyan("Not quite. Try again. Attempts left: $remainingAttempts")
    )
    class RecipeTimeNotAvailable(errorMessage: String) : RecipeTimeGameResult(
        messageResult =colors.red("Error: $errorMessage")
    )
}
