package Utils

import java.time.LocalDate

fun String.toDate(): LocalDate {
    return LocalDate.parse(this)
}