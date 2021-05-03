package com.weemusic.android.network

import com.google.gson.JsonObject
import com.weemusic.android.domain.Album
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface iTunesApi {

    @GET("/api/v1/us/apple-music/top-albums/all/{limit}/explicit.json")
    fun getTopAlbums(@Path("limit") limit: Int = 25): Observable<TopAlbumsResult>

}