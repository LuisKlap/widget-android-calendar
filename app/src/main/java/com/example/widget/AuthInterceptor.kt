package com.example.widget

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        
        Log.d("ABDGCHDGAJSKVCJV", "intercept")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "token $token")
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()
        return chain.proceed(request)
    }
}