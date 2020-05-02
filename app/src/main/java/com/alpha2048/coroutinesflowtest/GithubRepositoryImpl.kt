package com.alpha2048.coroutinesflowtest

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class GithubRepositoryImpl: GithubRepository {
    private var retrofit: Retrofit

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(
                OkHttpClient
                    .Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            )
            .build()
    }

    override suspend fun getRepositoryList(q: String, page: Int): Flow<RepoResponse> = flow {
        val service = retrofit.create(GithubApiInterface::class.java)
        emit(service.getGithubRepository(q, page))
    }.flowOn(Dispatchers.IO)
}

interface GithubApiInterface {
    @GET("/search/repositories")
    suspend fun getGithubRepository(@Query("q") q: String,
                                    @Query("page") page: Int): RepoResponse
}

data class RepoResponse (
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<RepoItemResponse>
)

data class RepoItemResponse (
    val id: Int,
    val name: String,
    val html_url: String,
    val stargazers_count: Int

)