package com.example.movieplayer.network

import com.example.movieplayer.domain.NowPlayingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("movie/now_playing")
    fun nowPlaying(@Query("language")language:String,@Query("page") page:Int): Call<NowPlayingResponse>
}