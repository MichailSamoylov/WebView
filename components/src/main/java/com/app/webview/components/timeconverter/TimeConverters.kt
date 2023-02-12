package com.app.webview.components.timeconverter

import java.text.SimpleDateFormat
import java.util.*

const val HOUR = 3599000L

const val DAY = 86399000L

fun convertTimeHourToString(date: Long): String =
	SimpleDateFormat("mm:ss", Locale("ru")).format(date)

fun convertTimeDayToString(date: Long): String =
	SimpleDateFormat("kk:mm:ss", Locale("ru")).format(date)