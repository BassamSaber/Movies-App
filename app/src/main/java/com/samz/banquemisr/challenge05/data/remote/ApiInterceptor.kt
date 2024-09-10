package com.samz.banquemisr.challenge05.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class ApiInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
        val updatedUrl = newRequest.url.newBuilder().apply {
            addQueryParameter("language", getLanguage())
        }.build()
        newRequest.newBuilder().apply {
            addHeader("Authorization", "Bearer $token")
            addHeader("accept", "application/json")
            url(updatedUrl)
        }.build()
        return chain.proceed(newRequest)
    }

    private fun getLanguage() = Locale.getDefault().run {
        language ?: "en-US"
    }
}