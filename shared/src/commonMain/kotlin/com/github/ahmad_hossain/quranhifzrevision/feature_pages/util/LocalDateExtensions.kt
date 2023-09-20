package com.github.ahmad_hossain.quranhifzrevision.feature_pages.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

fun LocalDate.Companion.now() = Clock.System.todayIn(TimeZone.currentSystemDefault())