package com.example.widget

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String
    ): List<Repo>

    @GET("repos/{username}/{repo}/commits")
    suspend fun getCommits(
        @Path("username") username: String,
        @Path("repo") repo: String,
        @Query("since") since: String,
        @Query("until") until: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): List<Commit>
}