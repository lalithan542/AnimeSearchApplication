package com.randstad.animesearchapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randstad.animesearchapplication.model.AnimeItem
import com.randstad.animesearchapplication.model.AnimeSearchResponse
import com.randstad.animesearchapplication.repository.AnimeAnimeRepository
import com.randstad.animesearchapplication.service.ServiceResult
import kotlinx.coroutines.launch

class AnimeSearchViewModel : ViewModel() {
    private lateinit var animeSearchRepository: AnimeAnimeRepository
    private var _animeListData = MutableLiveData<List<AnimeItem?>?>()
    val animeList: LiveData<List<AnimeItem?>?> = _animeListData
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun init(animeRepository: AnimeAnimeRepository) {
        this.animeSearchRepository = animeRepository
    }

    /**
     * this method triggers when giving input to search box
     * @param search term to be entered in search box
     */
    fun searchByAnimeTerm(anime: String) {
        if (anime.isNotEmpty()) {
            viewModelScope.launch {
                _isLoading.value = true
                when (
                    val result =
                        animeSearchRepository.fetchSearchData(anime)
                    ) {
                    is ServiceResult.Error -> {
                        _isLoading.value = false
                    }
                    is ServiceResult.Success -> {
                        _isLoading.value = false
                        val response = result.data as AnimeSearchResponse
                        _animeListData.value = response.results
                    }
                }
            }
        } else {
            _animeListData.value = emptyList()
        }
    }
}