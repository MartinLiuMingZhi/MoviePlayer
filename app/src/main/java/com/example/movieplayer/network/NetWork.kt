package com.example.movieplayer.network

import android.content.ContentValues
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object NetWork {
    private val service = RetrofitManager.create<Service>()

    suspend fun nowPlaying(language:String,page:Int) = service.nowPlaying(language,page).await()
    suspend fun upcoming(language:String,page:Int) = service.upcoming(language,page).await()

    suspend fun movieDetail(movieId:Int,language: String) = service.movieDetail(movieId, language).await()
    suspend fun movieActors(movieId:Int,language: String) = service.movieActors(movieId,language).await()
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    Log.d(ContentValues.TAG, "OnResponse: ${response.code()}")
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}