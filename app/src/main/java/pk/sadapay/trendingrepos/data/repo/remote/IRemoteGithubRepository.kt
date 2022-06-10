package pk.sadapay.trendingrepos.data.repo.remote

import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.dto.GithubTrendingRepos

interface IRemoteGithubRepository {
    suspend fun getTrendingRepositories(query:String): ApiResponse<GithubTrendingRepos>
}