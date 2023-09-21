package com.github.ahmad_hossain.quranhifzrevision

import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.DatabaseDriverFactory
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.createDatabase

class IosDependencies : Dependencies() {
    override val database = createDatabase(DatabaseDriverFactory())
}