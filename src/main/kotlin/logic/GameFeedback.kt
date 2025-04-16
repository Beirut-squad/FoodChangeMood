package org.example.logic

sealed class GameFeedback {
    class Correct(val actual: Int) : GameFeedback()
    class GameOver(val actual: Int, val name: String) : GameFeedback()
    class VeryClose(val attemptsLeft: Int) : GameFeedback()
    class WayOff(val attemptsLeft: Int) : GameFeedback()
    class NotQuite(val attemptsLeft: Int) : GameFeedback()
    class Error(val message: String) : GameFeedback()
}
