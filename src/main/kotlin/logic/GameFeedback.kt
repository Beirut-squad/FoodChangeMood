package org.example.logic

sealed class GameFeedback {
    class CorrectGuess(val actualTime: Int) : GameFeedback()
    class NoAttemptsLeft(val actualTime: Int) : GameFeedback()
    class GuessIsVeryClose(val remainingAttempts: Int) : GameFeedback()
    class GuessIsWayOff(val remainingAttempts: Int) : GameFeedback()
    class GuessIsNotQuiteRight(val remainingAttempts: Int) : GameFeedback()
    class RecipeTimeNotAvailable(val errorMessage: String) : GameFeedback()
}
