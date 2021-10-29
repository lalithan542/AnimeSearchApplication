package com.randstad.animesearchapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class AnimeSearchItem

@Parcelize
data class AnimeSearchResponse(
    val results: List<AnimeItem>
) : Parcelable, AnimeSearchItem()

@Parcelize
data class AnimeItem(
    val image_url: String?,
    val title: String?,
    val synopsis: String?
) : Parcelable, AnimeSearchItem()