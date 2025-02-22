package com.example.routify.model

data class Commit(
    val commit: CommitDetails
)

data class CommitDetails(
    val author: Author
)

data class Author(
    val date: String
)