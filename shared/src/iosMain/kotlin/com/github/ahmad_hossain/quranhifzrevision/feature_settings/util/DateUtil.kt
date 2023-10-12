package com.github.ahmad_hossain.quranhifzrevision.feature_settings.util

import kotlinx.datetime.LocalTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSHourCalendarUnit
import platform.Foundation.NSMinuteCalendarUnit

fun LocalTime.toDate(): NSDate {
    val localTime = this
    val comp = NSDateComponents().apply {
        this.hour = localTime.hour.toLong()
        this.minute = localTime.minute.toLong()
    }
    return NSCalendar.currentCalendar.dateFromComponents(comp)!!
}

fun NSDate.toLocalTime(): LocalTime {
    val cal = NSCalendar.currentCalendar
    val hour = cal.component(NSHourCalendarUnit, fromDate = this).toInt()
    val minute = cal.component(NSMinuteCalendarUnit, fromDate = this).toInt()
    return LocalTime(hour, minute)
}