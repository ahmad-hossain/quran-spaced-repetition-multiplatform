package com.github.ahmad_hossain.quranhifzrevision.feature_pages.data

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.github.ahmad_hossain.quranhifzrevision.PageDatabase
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.domain.InsertDefaultPages
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.util.PageUtil

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver = AndroidSqliteDriver(
        PageDatabase.Schema,
        context,
        PageUtil.DATABASE_NAME,
        callback = object : AndroidSqliteDriver.Callback(PageDatabase.Schema) {
            override fun onConfigure(db: SupportSQLiteDatabase) {
                super.onConfigure(db)
                db.enableWriteAheadLogging()
            }

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                InsertDefaultPages { sql ->
                    db.execSQL(sql)
                }
            }
        }
    )
}