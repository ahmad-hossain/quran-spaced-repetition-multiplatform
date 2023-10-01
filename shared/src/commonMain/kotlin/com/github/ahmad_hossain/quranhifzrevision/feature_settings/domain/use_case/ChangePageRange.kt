package com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.use_case

import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source.PageDataSource
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.util.PageUtil
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePageRange (
    private val settingsRepo: SettingsRepository,
    private val pageRepo: PageDataSource
) {

    suspend operator fun invoke(newPageRange: IntRange) = withContext(Dispatchers.IO) {
        val data = settingsRepo.getUserPreferences()
        val oldPageRange = data.startPage..data.endPage
        // TODO
//        Timber.d("oldPageRange: $oldPageRange newPageRange: $newPageRange")

        launch {
            val pagesToAdd = newPageRange.subtract(oldPageRange)
            // TODO
//            Timber.d("pagesToAdd: $pagesToAdd")
            pagesToAdd.forEach {
                pageRepo.insertPage(PageUtil.defaultPage(pageNumber = it))
            }
        }
        launch {
            val pagesToDelete = oldPageRange.subtract(newPageRange)
            // TODO
//            Timber.d("pagesToDelete: $pagesToDelete")
            pagesToDelete.forEach {
                pageRepo.deletePage(PageUtil.defaultPage(pageNumber = it))
            }
        }
    }
}