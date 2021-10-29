package com.randstad.animesearchapplication.repository

import com.randstad.animesearchapplication.model.AnimeSearchItem
import com.randstad.animesearchapplication.model.AnimeSearchResponse
import com.randstad.animesearchapplication.service.AnimSearchService
import com.randstad.animesearchapplication.service.RetrofitCallHandler
import com.randstad.animesearchapplication.service.RetrofitService
import com.randstad.animesearchapplication.service.ServiceResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeSearchRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val animeService: AnimSearchService = RetrofitService.createService(
        AnimSearchService::class.java
    )
) : AnimeAnimeRepository {

    override suspend fun fetchSearchData(anime: String): ServiceResult<AnimeSearchItem> {
        val result = withContext(ioDispatcher) {
            RetrofitCallHandler.processCall {
                animeService.getSearchByAnime(anime)
            }
        }

        return when(result) {
            is ServiceResult.Success -> transformResponseToAnimeSearchItem(result.data)
            is ServiceResult.Error -> result
        }
    }

    private fun transformResponseToAnimeSearchItem(response: AnimeSearchResponse) : ServiceResult<AnimeSearchItem> {
        response.apply {

            response.let {
                return ServiceResult.Success(it)
            }

            return ServiceResult.Error(Exception())
        }
    }
}