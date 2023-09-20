package com.github.ahmad_hossain.quranhifzrevision.feature_pages.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.github.ahmad_hossain.quranhifzrevision.PageDatabase
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.util.PageUtil

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver = AndroidSqliteDriver(
        PageDatabase.Schema,
        context,
        PageUtil.DATABASE_NAME
    )
}