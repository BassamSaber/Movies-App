package com.samz.banquemisr.challenge05.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class ApiInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val updatedUrl = request.url.newBuilder().apply {
            addQueryParameter("language", getLanguage())
        }.build()
        val newRequest = request.newBuilder().apply {
            addHeader("Authorization", "Bearer $token")
            addHeader("accept", "application/json")
            url(updatedUrl)
        }
        return chain.proceed(newRequest.build())
    }

    private fun getLanguage() = Locale.getDefault().run {
        language ?: "en-US"
    }
}