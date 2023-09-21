package com.github.ahmad_hossain.quranhifzrevision

import android.content.Context
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.DatabaseDriverFactory
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.createDatabase

class AndroidDependencies(
    context: Context,
) : Dependencies() {
    override val database = createDatabase(DatabaseDriverFactory(context))
}