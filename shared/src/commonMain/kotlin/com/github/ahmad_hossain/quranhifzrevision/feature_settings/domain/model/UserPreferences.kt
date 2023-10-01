package com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.model

import kotlinx.datetime.LocalTime

data class UserPreferences(
    val startPage: Int = 1,
    val endPage: Int = 611,
    val notificationTime: LocalTime = LocalTime(0, 0)
)