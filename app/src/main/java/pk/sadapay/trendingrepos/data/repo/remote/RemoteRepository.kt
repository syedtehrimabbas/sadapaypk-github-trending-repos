package pk.sadapay.trendingrepos.data.repo.remote

import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.base.BaseRepository
import pk.sadapay.trendingrepos.data.dto.GithubTrendingRepos
import pk.sadapay.trendingrepos.data.repo.IGithubRepository
import javax.inject.Inject

class RemoteRepository @Inject
constructor(
    private val service: GithubRepositoryService
) : BaseRepository(), IGithubRepository {

    override suspend fun getTrendingRepositories(query: String): ApiResponse<GithubTrendingRepos> =
        executeSafely(
            call =
            {
                service.getTrendingRepositories(query)
            }
        )
}