package com.github.ahmad_hossain.quranhifzrevision

import app.cash.sqldelight.db.SqlDriver
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.DatabaseDriverFactory

class IosDependencies : Dependencies() {
    override val driver: SqlDriver = DatabaseDriverFactory().create()
}