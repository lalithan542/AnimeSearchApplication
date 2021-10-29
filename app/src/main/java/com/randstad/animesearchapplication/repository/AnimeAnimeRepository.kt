package com.randstad.animesearchapplication.repository

import com.randstad.animesearchapplication.model.AnimeSearchItem
import com.randstad.animesearchapplication.service.ServiceResult

interface AnimeAnimeRepository {
    suspend fun fetchSearchData(anime: String): ServiceResult<AnimeSearchItem>
}