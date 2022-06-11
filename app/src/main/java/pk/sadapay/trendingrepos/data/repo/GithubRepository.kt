package pk.sadapay.trendingrepos.data.repo

import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.dto.GithubTrendingRepos
import pk.sadapay.trendingrepos.data.repo.local.ILocalGithubRepository
import pk.sadapay.trendingrepos.data.repo.remote.IRemoteGithubRepository


class GithubRepository constructor(
    private val localGithubRepository: ILocalGithubRepository,
    private val remoteGithubRepository: IRemoteGithubRepository
) : IGithubRepository {
    override suspend fun loadGithubTopRepositories(
        query: String,
        isRefresh: Boolean
    ): ApiResponse<GithubTrendingRepos> {

        val localData = localGithubRepository.getTopRepos()
        return when {
            isRefresh.not() && localData.isNotEmpty() -> {
                ApiResponse.Success(
                    200,
                    GithubTrendingRepos(repos = localData)
                )
            }
            else -> {
                var response = remoteGithubRepository.getTrendingRepositories(query)
                if (response is ApiResponse.Success) {
                    response.data.repos?.let { localGithubRepository.insertTopRepos(it) }
                } else {
                    if (localData.isNotEmpty())
                        response = ApiResponse.Success(
                            200,
                            GithubTrendingRepos(repos = localData)
                        )
                }
                response
            }
        }
    }
}