
package utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

fun String.toDate(): LocalDate {
    return LocalDate.parse(this)
}

fun String.checkDateFormat(){
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    dateFormat.isLenient = false
    dateFormat.parse(this)
}