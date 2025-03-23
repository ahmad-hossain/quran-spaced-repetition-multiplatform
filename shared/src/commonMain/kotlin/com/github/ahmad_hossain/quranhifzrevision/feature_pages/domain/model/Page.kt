package com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.util.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.periodUntil

@TypeConverters(Page.Converters::class)
@Entity
data class Page(
    /** Page Number in the Quran */
    @PrimaryKey
    val pageNumber: Int,
    /** Number of days to wait before the next review */
    val interval: Int = 0,
    /** Number of continuous *correct* repetitions */
    val repetitions: Int = 0,
    /** Easiness factor - calculated based off how easily info. is remembered */
    val eFactor: Double = 2.5,
    /** Date this Page is due for next review, or null if never reviewed */
    val dueDate: LocalDate? = null
) {
    val relativeDueDate: String?
        get() = dueDate?.let {
            val periodTillDueDate = LocalDate.now().periodUntil(it)
            var s = ""
            if (periodTillDueDate.years != 0)
                s += "${periodTillDueDate.years}Y "
            if (periodTillDueDate.months != 0)
                s += "${periodTillDueDate.months}M "
            if (periodTillDueDate.days != 0 || s.isEmpty())
                s += "${periodTillDueDate.days}D"
            s.trim()
        }
    val shouldGradePage: Boolean
        get() = dueDate == null || dueDate.toEpochDays() <= LocalDate.now().toEpochDays()

    object Converters {
        @TypeConverter
        fun epochDayToLocalDate(value: Int?): LocalDate? =
            value?.let { LocalDate.fromEpochDays(it) }

        @TypeConverter
        fun localDateToEpochDay(date: LocalDate?): Int? = date?.toEpochDays()
    }
}
