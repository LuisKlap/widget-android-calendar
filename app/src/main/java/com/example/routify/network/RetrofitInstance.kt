package com.example.routify.network

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.github.com/"

    private val client = OkHttpClient.Builder().build()

    val api: GitHubApiService by lazy {
        Log.d("ROUTIFYLOG.RetrofitInstance.api", "Creating Retrofit instance")
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApiService::class.java)
    }
}