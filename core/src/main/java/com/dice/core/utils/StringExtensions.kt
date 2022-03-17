package com.dice.core.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object StringExtensions {
    fun String.toLocalDate(dateFormat: String? = "yyyy-MM-dd"): String {
        if (this.isBlank()) return ""
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = simpleDateFormat.parse(this) ?: ""
        return DateFormat.getDateInstance(DateFormat.LONG, Constants.LOCALE_INDONESIA).format(date)
    }
}