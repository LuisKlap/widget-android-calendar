package com.example.routify.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("ROUTIFYLOG.AuthInterceptor.intercept", "Adding authorization header")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "token $token")
            .build()
        return chain.proceed(request)
    }
}