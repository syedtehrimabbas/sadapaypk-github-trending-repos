package pk.sadapay.trendingrepos.data.repo.remote

import pk.sadapay.trendingrepos.data.dto.GithubTrendingRepos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepositoryService {
    @GET("search/repositories")
    suspend fun getTrendingRepositories(@Query("q") query: String): Response<GithubTrendingRepos>
}