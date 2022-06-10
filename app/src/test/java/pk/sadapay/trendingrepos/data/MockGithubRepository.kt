package pk.sadapay.trendingrepos.data

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import pk.sadapay.trendingrepos.common.Utils.readFileFromTestResources
import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.base.error.ApiError
import pk.sadapay.trendingrepos.data.dto.GithubTrendingRepos
import pk.sadapay.trendingrepos.data.repo.IGithubRepository

class MockGithubRepository : IGithubRepository {
    override suspend fun loadGithubTopRepositories(
        query: String,
        isRefresh: Boolean
    ): ApiResponse<GithubTrendingRepos> {
        val response = getMockResponse()
        return if (query.isNotBlank())
            ApiResponse.Success(200, response)
        else
            ApiResponse.Error(ApiError(400, "Bad request"))
    }

    private fun getMockResponse(): GithubTrendingRepos {
        val gson = GsonBuilder().create()
        val itemType = object : TypeToken<GithubTrendingRepos>() {}.type
        return gson.fromJson(readFileFromTestResources("success_response_api.json"), itemType)
    }
}