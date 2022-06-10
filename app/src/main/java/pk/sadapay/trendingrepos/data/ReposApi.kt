package pk.sadapay.trendingrepos.data

import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.dto.GithubTrendingRepos

interface ReposApi {
    suspend fun getTopApi(query:String): ApiResponse<GithubTrendingRepos>
}