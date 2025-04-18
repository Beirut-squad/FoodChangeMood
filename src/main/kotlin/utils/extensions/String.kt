package org.example.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.toDateOrNull(format: String = "yyyy-MM-dd"): Date? {
    return try {
        SimpleDateFormat(format).parse(this)
    } catch (e: Exception) {
        null
    }
}