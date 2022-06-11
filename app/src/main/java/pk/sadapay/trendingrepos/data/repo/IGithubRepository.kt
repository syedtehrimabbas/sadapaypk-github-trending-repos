package pk.sadapay.trendingrepos.data.repo

import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.dto.GithubTrendingRepos

interface IGithubRepository {
    suspend fun getTrendingRepositories(query:String): ApiResponse<GithubTrendingRepos>
}