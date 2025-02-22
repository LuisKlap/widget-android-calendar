package com.example.routify.network

import com.example.routify.model.Commit
import com.example.routify.model.Repo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): List<Repo>

    @GET("repos/{username}/{repo}/commits")
    suspend fun getCommits(
        @Path("username") username: String,
        @Path("repo") repo: String,
        @Query("since") since: String,
        @Query("until") until: String,
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): List<Commit>
}