package org.example.logic.use_case

sealed class GameFeedbackUseCase {
    class CorrectGuess(val actualTime: Int) : GameFeedbackUseCase()
    class NoAttemptsLeft(val actualTime: Int) : GameFeedbackUseCase()
    class GuessIsVeryClose(val remainingAttempts: Int) : GameFeedbackUseCase()
    class GuessIsWayOff(val remainingAttempts: Int) : GameFeedbackUseCase()
    class GuessIsNotQuiteRight(val remainingAttempts: Int) : GameFeedbackUseCase()
    class RecipeTimeNotAvailable(val errorMessage: String) : GameFeedbackUseCase()
}
