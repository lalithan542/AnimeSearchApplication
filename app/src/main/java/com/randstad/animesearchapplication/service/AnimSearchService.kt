package com.randstad.animesearchapplication.service

import com.randstad.animesearchapplication.model.AnimeSearchResponse
import com.randstad.animesearchapplication.utils.QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimSearchService {

    @GET("v3/search/anime")
    suspend fun getSearchByAnime(@Query(QUERY) animeQuery: String): Response<AnimeSearchResponse>
}