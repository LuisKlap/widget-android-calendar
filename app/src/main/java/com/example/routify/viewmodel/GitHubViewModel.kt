package com.example.routify.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routify.repository.GitHubRepository
import kotlinx.coroutines.launch

class GitHubViewModel : ViewModel() {
    private val repository = GitHubRepository()

    private val _commitDates = MutableLiveData<List<String>>()
    val commitDates: LiveData<List<String>> get() = _commitDates

    fun fetchReposAndCommits(username: String, token: String, since: String, until: String) {
        Log.d("ROUTIFYLOG.GitHubViewModel.fetchReposAndCommits", "Fetching repos and commits for username: $username")
        viewModelScope.launch {
            try {
                val repos = repository.getRepos(username, token)
                Log.d("ROUTIFYLOG.GitHubViewModel.fetchReposAndCommits", "Fetched repos: $repos")
                val allCommitDates = mutableListOf<String>()

                for (repo in repos) {
                    var page = 1
                    while (true) {
                        val commits = repository.getCommits(username, repo.name, since, until, token, page)
                        Log.d("ROUTIFYLOG.GitHubViewModel.fetchReposAndCommits", "Fetched commits for repo ${repo.name} on page $page: $commits")
                        if (commits.isEmpty()) break
                        val dates = commits.map { it.commit.author.date }
                        allCommitDates.addAll(dates)
                        page++
                    }
                }

                _commitDates.postValue(allCommitDates)
                Log.d("ROUTIFYLOG.GitHubViewModel.fetchReposAndCommits", "All commit dates: $allCommitDates")
            } catch (e: Exception) {
                Log.e("ROUTIFYLOG.GitHubViewModel.fetchReposAndCommits", "Error fetching repos and commits", e)
            }
        }
    }
}