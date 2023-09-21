package com.github.ahmad_hossain.quranhifzrevision

import android.content.Context
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.DatabaseDriverFactory

class AndroidDependencies(
    context: Context,
) : Dependencies() {
    override val driver = DatabaseDriverFactory(context).create()
}