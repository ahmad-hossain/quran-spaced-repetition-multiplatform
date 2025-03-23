package com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain

import com.github.ahmad_hossain.quranhifzrevision.feature_pages.util.PageUtil

object InsertDefaultPages {
    operator fun invoke(insertPage: (sql: String) -> Unit) {
        (1..611).forEach { pageNumber ->
            val defaultPage = PageUtil.defaultPage(pageNumber = pageNumber)
            insertPage("INSERT INTO Page VALUES ($pageNumber, ${defaultPage.interval}, ${defaultPage.repetitions}, ${defaultPage.eFactor}, ${defaultPage.dueDate})")
        }
    }
}