package com.example.routify.repository

import android.util.Log
import com.example.routify.model.Commit
import com.example.routify.model.Repo
import com.example.routify.network.GitHubApiService
import com.example.routify.network.RetrofitInstance

class GitHubRepository {
    private val api: GitHubApiService = RetrofitInstance.api

    suspend fun getRepos(username: String, token: String): List<Repo> {
        Log.d("ROUTIFYLOG.GitHubRepository.getRepos", "Fetching repos for username: $username")
        return api.getRepos(username, "token $token")
    }

    suspend fun getCommits(username: String, repo: String, since: String, until: String, token: String, page: Int): List<Commit> {
        Log.d("ROUTIFYLOG.GitHubRepository.getCommits", "Fetching commits for repo: $repo, page: $page")
        return api.getCommits(username, repo, since, until, page = page, token = "token $token")
    }
}