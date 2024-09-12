package com.samz.banquemisr.challenge05.di

import android.app.Application
import com.samz.banquemisr.challenge05.BuildConfig
import com.samz.banquemisr.challenge05.data.remote.ApiInterceptor
import com.samz.banquemisr.challenge05.data.remote.ApiService
import com.samz.banquemisr.challenge05.data.remote.ConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseURL() = ApiService.BASE_URL


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseURL: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiInterceptor(): ApiInterceptor =
        ApiInterceptor(BuildConfig.AuthToken)

    @Provides
    @Singleton
    fun provideConnectionInterceptor(application: Application): ConnectionInterceptor =
        ConnectionInterceptor(application)


    @Provides
    @Singleton
    fun provideOkHttpClient(
        apiInterceptor: ApiInterceptor,
        connectionInterceptor: ConnectionInterceptor
    ): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.addInterceptor(connectionInterceptor)
        okHttpClientBuilder.addInterceptor(interceptor)
        okHttpClientBuilder.addInterceptor(apiInterceptor)

        okHttpClientBuilder
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        return okHttpClientBuilder.build()
    }
}
