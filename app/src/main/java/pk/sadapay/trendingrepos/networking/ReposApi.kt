package pk.sadapay.trendingrepos.networking

import pk.sadapay.trendingrepos.networking.base.ApiResponse
import pk.sadapay.trendingrepos.networking.dto.GithubTrendingRepos

interface ReposApi {
    suspend fun getTopApi(query:String): ApiResponse<GithubTrendingRepos>
}