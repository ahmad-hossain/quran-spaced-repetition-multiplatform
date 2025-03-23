package com.github.ahmad_hossain.quranhifzrevision.feature_pages.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.github.ahmad_hossain.quranhifzrevision.PageDatabase
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.InsertDefaultPages
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.util.PageUtil

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(
            PageDatabase.Schema,
            PageUtil.DATABASE_NAME,
            onCreate = { driver ->
                InsertDefaultPages { sql ->
                    driver.execute(
                        identifier = null,
                        sql = sql,
                        parameters = 0,
                    )
                }
            }
        )
    }
}