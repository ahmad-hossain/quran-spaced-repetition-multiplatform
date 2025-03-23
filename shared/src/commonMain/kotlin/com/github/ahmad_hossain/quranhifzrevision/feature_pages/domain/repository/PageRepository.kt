package com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.repository

import com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.model.Page
import kotlinx.coroutines.flow.Flow

interface PageRepository {
    fun getPages(): Flow<List<Page>>

    fun getPagesDueToday(): Flow<List<Page>>

    suspend fun updatePage(page: Page)

    suspend fun insertPage(page: Page)

    suspend fun deletePage(page: Page)

    suspend fun checkpoint()
}
