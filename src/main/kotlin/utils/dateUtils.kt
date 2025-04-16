package utils

import java.time.LocalDate

fun stringToDate(date: String): LocalDate {
    return LocalDate.parse(date)
}