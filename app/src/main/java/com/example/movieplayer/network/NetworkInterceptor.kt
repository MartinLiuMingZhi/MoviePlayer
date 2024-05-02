package com.example.movieplayer.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

object NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        Log.d("NetworkInterceptor", "Request URL: $url") // 记录完整的请求 URL
        return chain.proceed(request)
    }
}
