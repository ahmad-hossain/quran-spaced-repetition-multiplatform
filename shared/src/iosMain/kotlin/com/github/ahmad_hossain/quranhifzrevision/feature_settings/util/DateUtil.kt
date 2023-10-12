package com.github.ahmad_hossain.quranhifzrevision.feature_settings.util

import kotlinx.datetime.LocalTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents

fun LocalTime.toDate(): NSDate {
    val localTime = this
    val comp = NSDateComponents().apply {
        this.hour = localTime.hour.toLong()
        this.minute = localTime.minute.toLong()
    }
    return NSCalendar.currentCalendar.dateFromComponents(comp)!!
}