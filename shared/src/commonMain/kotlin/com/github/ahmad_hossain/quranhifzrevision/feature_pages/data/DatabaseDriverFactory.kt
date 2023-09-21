package com.github.ahmad_hossain.quranhifzrevision.feature_pages.data

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.github.ahmad_hossain.quranhifzrevision.PageDatabase
import com.github.ahmadhossain.quranhifzrevision.Page

expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}

fun createDatabase(driverFactory: DatabaseDriverFactory) = PageDatabase(
    driverFactory.create(),
    Page.Adapter(
        pageNumberAdapter = IntColumnAdapter,
        intervalAdapter = IntColumnAdapter,
        repetitionsAdapter = IntColumnAdapter
    )
)