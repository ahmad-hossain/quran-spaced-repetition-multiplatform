package com.github.ahmad_hossain.quranhifzrevision.feature_pages.data

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}