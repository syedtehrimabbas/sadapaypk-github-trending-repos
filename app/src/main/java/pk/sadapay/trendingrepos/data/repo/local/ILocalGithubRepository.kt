package pk.sadapay.trendingrepos.data.repo.local

import pk.sadapay.trendingrepos.data.dto.Repo


interface ILocalGithubRepository {
    suspend fun getTopRepos(): List<Repo>
    suspend fun insertTopRepos(repos: List<Repo>)
}