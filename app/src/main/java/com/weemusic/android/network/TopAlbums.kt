package com.weemusic.android.network

import com.google.gson.annotations.SerializedName
import com.weemusic.android.domain.Album

class TopAlbums(albums : List<Album>) {

    @SerializedName("results")
    var albums : List<Album> = albums

    fun get() = albums

}