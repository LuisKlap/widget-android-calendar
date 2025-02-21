package com.example.widget

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GitHubService(private val api: GitHubApi = RetrofitInstance.api) {
    suspend fun fetchContributions(username: String): Int {
        Log.d("ABDGCHDGAJSKVCJV", "Fetching contributions for user: $username")
        val year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val since = "$year-01-01T00:00:00Z"
        val until = "$year-12-31T23:59:59Z"

        val repos = api.getRepos(username)
        var totalCommits = 0

        for (repo in repos) {
            var page = 1
            while (true) {
                val commits = api.getCommits(username, repo.name, since, until, 100, page)
                if (commits.isEmpty()) break
                totalCommits += commits.size
                page++
            }
        }

        Log.d("ABDGCHDGAJSKVCJV", "Total commits: $totalCommits")
        return totalCommits
    }
}