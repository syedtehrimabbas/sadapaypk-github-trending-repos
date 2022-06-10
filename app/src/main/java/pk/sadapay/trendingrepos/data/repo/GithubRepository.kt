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

        val repos = localGithubRepository.getTopRepos()
        return when {
            !isRefresh && repos.isNotEmpty() -> {
                ApiResponse.Success(
                    200,
                    GithubTrendingRepos(repos = repos)
                )
            }
            else -> {
                val response = remoteGithubRepository.getTrendingRepositories(query)
                if (response is ApiResponse.Success) {
                    response.data.repos?.let { localGithubRepository.insertTopRepos(it) }
                }
                response
            }
        }
    }
}