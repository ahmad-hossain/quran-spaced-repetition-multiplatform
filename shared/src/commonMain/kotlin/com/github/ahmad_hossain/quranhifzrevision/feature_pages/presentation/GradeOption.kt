package com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation

import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import dev.icerock.moko.resources.StringResource

data class GradeOption(
    val grade: Int,
    val emoji: String,
    val textRes: StringResource,
) {
    companion object {
        val gradeOptions = listOf(
            GradeOption(
                grade = 5,
                emoji = "😎",
                textRes = SharedRes.strings.grade_5_desc,
            ),
            GradeOption(
                grade = 4,
                emoji = "🙂",
                textRes = SharedRes.strings.grade_4_desc,
            ),
            GradeOption(
                grade = 3,
                emoji = "☹️",
                textRes = SharedRes.strings.grade_3_desc,
            ),
            GradeOption(
                grade = 2,
                emoji = "😢",
                textRes = SharedRes.strings.grade_2_desc,
            ),
            GradeOption(
                grade = 1,
                emoji = "😭",
                textRes = SharedRes.strings.grade_1_desc,
            ),
            GradeOption(
                grade = 0,
                emoji = "💀",
                textRes = SharedRes.strings.grade_0_desc,
            ),
        )
    }
}
