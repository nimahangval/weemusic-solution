package com.weemusic.android.network

import com.google.gson.annotations.SerializedName

class TopAlbumsResult(topAlbums: TopAlbums) {
    @SerializedName("feed")
    var topAlbums : TopAlbums = topAlbums

    fun get() = topAlbums
}