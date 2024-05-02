package com.example.movieplayer.network

import com.example.movieplayer.domain.ActorResponse
import com.example.movieplayer.domain.DetailResponse
import com.example.movieplayer.domain.FilmResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("movie/now_playing")
    fun nowPlaying(@Query("language")language:String,@Query("page") page:Int): Call<FilmResponse>

    @GET("movie/upcoming")
    fun upcoming(@Query("language")language:String,@Query("page") page:Int): Call<FilmResponse>

    @GET("movie/{movie_id}")
    fun movieDetail(@Path("movie_id") movieId:Int,@Query("language")language:String):Call<DetailResponse>

    @GET("movie/{movie_id}/credits")
    fun movieActors(@Path("movie_id") movieId:Int,@Query("language")language:String):Call<ActorResponse>
}