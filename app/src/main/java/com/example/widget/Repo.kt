package com.example.widget

data class Repo(
    val name: String
)

data class Commit(
    val commit: CommitDetails
)

data class CommitDetails(
    val author: Author
)

data class Author(
    val date: String
)