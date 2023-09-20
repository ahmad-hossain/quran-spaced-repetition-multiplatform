package com.github.ahmad_hossain.quranhifzrevision.feature_pages.util

import com.github.ahmadhossain.quranhifzrevision.Page
import kotlinx.datetime.LocalDate
import kotlinx.datetime.periodUntil

object PageUtil {
    const val DATABASE_NAME = "page_db.db"

    fun defaultPage(
        pageNumber: Int,
        interval: Int = 0,
        repetitions: Int = 0,
        eFactor: Double = 2.5,
        dueDate: Long? = null
    ) = Page(
        pageNumber,
        interval,
        repetitions,
        eFactor,
        dueDate
    )

    val Page.localDueDate: LocalDate?
        get() = if (dueDate == null) null else LocalDate.fromEpochDays(dueDate.toInt())

    val Page.relativeDueDate: String?
        get() = localDueDate?.let {
            val periodTillDueDate = LocalDate.now().periodUntil(it)
            var s = ""
            if (periodTillDueDate.years != 0)
                s += "${periodTillDueDate.years}Y "
            if (periodTillDueDate.months != 0)
                s += "${periodTillDueDate.months}M "
            if (periodTillDueDate.days != 0 || s.isEmpty())
                s += "${periodTillDueDate.days}D"
            return s.trim()
        }
}