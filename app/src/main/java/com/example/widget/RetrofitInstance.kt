package com.example.widget

import io.github.cdimascio.dotenv.Dotenv
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.github.com/"
    
    private val dotenv = Dotenv.load()
    private val TOKEN = ""

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(TOKEN))
        .build()

    val api: GitHubApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }
}