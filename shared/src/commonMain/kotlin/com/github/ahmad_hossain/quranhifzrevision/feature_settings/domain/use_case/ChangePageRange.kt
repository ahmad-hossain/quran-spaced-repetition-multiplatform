package com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.use_case

import com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.model.Page
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.repository.PageRepository
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePageRange (
    private val settingsRepo: SettingsRepository,
    private val pageRepository: PageRepository
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
                pageRepository.insertPage(Page(pageNumber = it))
            }
        }
        launch {
            val pagesToDelete = oldPageRange.subtract(newPageRange)
            // TODO
//            Timber.d("pagesToDelete: $pagesToDelete")
            pagesToDelete.forEach {
                pageRepository.deletePage(Page(pageNumber = it))
            }
        }
    }
}