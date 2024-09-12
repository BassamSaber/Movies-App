package com.samz.banquemisr.challenge05.data.remote

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class ConnectionInterceptor @Inject constructor(private val app: Application) : Interceptor {

    private fun checkConnection(): Boolean {
        val cm: ConnectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!checkConnection()) {
            throw NoConnectionException();
        }
        return chain.proceed(chain.request())

    }
}