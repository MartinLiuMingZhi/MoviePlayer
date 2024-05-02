package com.example.movieplayer.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNzBhNTAzM2RkY2M3ODkyYjcwNjEwYzIxNGVlMzQ1MiIsInN1YiI6IjY2MmVmOGQxZTMzZjgzMDEyMjIxZDVkZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.mci_jNf1ZKKWBnzMjEa3tVICyfVfZkLsQHLkJNz6mtE"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun createOkHttpClient() : OkHttpClient {
        val okBuilder = OkHttpClient.Builder().apply {
            addInterceptor(NetworkInterceptor)
            addInterceptor {
                val original = it.request()
                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("connection", "Keep-Alive")
                    .header("Authorization", "Bearer $TOKEN")
                    .method(original.method(),original.body())
                    .build()
                it.proceed(request)
            }
        }
            .connectTimeout(30, TimeUnit.SECONDS) // 设置连接超时时间为 30 秒
            . readTimeout(30, TimeUnit.SECONDS) // 设置读取超时时间为 30 秒
        return  okBuilder.build()
    }

    fun <T> create(serviceClass: Class<T>) : T = retrofit.create(serviceClass)
    inline fun <reified T> create() : T = create(T::class.java)
}