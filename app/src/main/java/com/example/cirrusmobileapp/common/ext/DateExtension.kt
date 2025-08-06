package com.example.cirrusmobileapp.common.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toIsoDateTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return formatter.format(Date(this))
}

fun getNowDateTimeString(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}