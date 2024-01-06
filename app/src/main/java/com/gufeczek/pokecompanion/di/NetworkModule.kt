package com.gufeczek.pokecompanion.di

import com.gufeczek.pokecompanion.core.network.HttpRequestInterceptor
import com.gufeczek.pokecompanion.core.network.NetworkResultCallAdapterFactory
import com.gufeczek.pokecompanion.data.remote.client.PokemonClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = " https://pokeapi.co/api/v2/"
    private val logInterceptor =
        HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.HEADERS)


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            .addInterceptor(logInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonClient(retrofit: Retrofit): PokemonClient =
        retrofit.create(PokemonClient::class.java)
}