package com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain

import com.github.ahmadhossain.quranhifzrevision.Page
import kotlin.math.roundToInt

/** Utilizes the SuperMemo 2 algorithm to calculate a Page's new interval, repetition, and eFactor */
object SuperMemo {

    operator fun invoke(
        page: Page,
        grade: Int,
    ): Page {
        val nextInterval: Int
        val nextRepetition: Int
        var nextEfactor: Double

        if (grade >= 3) {
            nextInterval = when (page.repetitions) {
                0 -> 1
                1 -> 6
                else -> (page.interval * page.eFactor).roundToInt()
            }
            nextRepetition = page.repetitions + 1
        } else {
            nextInterval = 1
            nextRepetition = 0
        }

        nextEfactor = page.eFactor + (0.1 - (5 - grade) * (0.08 + (5 - grade) * 0.02))

        if (nextEfactor < 1.3) nextEfactor = 1.3

        return page.copy(
            interval = nextInterval,
            repetitions = nextRepetition,
            eFactor = nextEfactor,
        )
    }
}