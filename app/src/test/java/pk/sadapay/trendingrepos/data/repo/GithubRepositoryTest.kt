package pk.sadapay.trendingrepos.data.repo

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test
import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.dto.Repo
import pk.sadapay.trendingrepos.data.repo.local.ILocalGithubRepository
import pk.sadapay.trendingrepos.data.repo.remote.IRemoteGithubRepository

@ExperimentalCoroutinesApi
class GithubRepositoryTest {
    lateinit var sut: IGithubRepository

    @Test
    fun `successful data fetching from local or remote`() = runTest {

        val remoteMock = mockk<IRemoteGithubRepository> {
            coEvery { getTrendingRepositories("query") } returns ApiResponse.Success(200, mockk {
                every { repos } returns listOf(Repo())
                coEvery { totalCount } returns 1
            })
        }

        val localMock = mockk<ILocalGithubRepository> {
            coEvery { getTopRepos() } returns listOf()
            coEvery { insertTopRepos(listOf(Repo())) } returns mockk()
        }

        sut = GithubRepository(localMock, remoteMock)

        val actual = sut.loadGithubTopRepositories("query", true)
        val expected = listOf(Repo())
        if (actual is ApiResponse.Success) {
            Assert.assertEquals(actual.data.repos, expected)
            Assert.assertEquals(actual.data.totalCount, 1)
        }

        coVerify {
            remoteMock.getTrendingRepositories("query")
            localMock.getTopRepos()
        }
    }

    @Test
    fun `successful data fetching from remote when no stale data found`() = runTest {

        val remoteMock = mockk<IRemoteGithubRepository> {
            coEvery { getTrendingRepositories("query") } returns ApiResponse.Success(200, mockk {
                coEvery { repos } returns listOf(Repo())
                coEvery { totalCount } returns 1
            })
        }

        val localMock = mockk<ILocalGithubRepository> {
            coEvery { getTopRepos() } returns listOf()
            coEvery { insertTopRepos(listOf(Repo())) } returns mockk()
        }

        sut = GithubRepository(localMock, remoteMock)

        val actual = sut.loadGithubTopRepositories("query", true)
        val expected = listOf(Repo())

        if (actual is ApiResponse.Success) {
            Assert.assertEquals(actual.data.repos, expected)
            Assert.assertEquals(actual.data.totalCount, 1)
        }

        coVerify {
            remoteMock.getTrendingRepositories("query")
            localMock.getTopRepos()
            localMock.insertTopRepos(listOf(Repo()))
        }
    }

    @Test
    fun `fail api due to bad network connection and no stale data`() = runTest {
        val remoteMock = mockk<IRemoteGithubRepository> {
            coEvery { getTrendingRepositories("query") } returns ApiResponse.Error(mockk {
                coEvery { message } returns "No Internet"
                coEvery { statusCode } returns 504
            })
        }

        val localMock = mockk<ILocalGithubRepository> {
            coEvery { getTopRepos() } returns listOf()
        }

        sut = GithubRepository(localMock, remoteMock)

        val actual = sut.loadGithubTopRepositories("query", false)
        val expected = 504

        if (actual is ApiResponse.Error) {
            Assert.assertEquals(actual.error.statusCode, expected)
        }

        coVerify {
            remoteMock.getTrendingRepositories("query")
            localMock.getTopRepos()
        }
    }

    @After
    fun cleanUp() {
        clearAllMocks()
    }
}