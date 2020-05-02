package com.alpha2048.coroutinesflowtest

import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getRepositoryList(q: String, page: Int): Flow<RepoResponse>
}