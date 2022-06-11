package pk.sadapay.trendingrepos.data.repo.remote

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test
import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.dto.Repo
import pk.sadapay.trendingrepos.data.repo.IGithubRepository

@ExperimentalCoroutinesApi
class RemoteGithubRepositoryTest {
    lateinit var sut: IGithubRepository

    @Test
    fun `test when remote data successfully fetch`() = runTest {
        sut = mockk {
            coEvery { getTrendingRepositories("query") } returns ApiResponse.Success(200, mockk {
                coEvery { repos } returns listOf(Repo())
                coEvery { totalCount } returns 1
            })
        }
        val actual = sut.getTrendingRepositories("query")
        val expected = listOf(Repo())
        if (actual is ApiResponse.Success) {
            Assert.assertEquals(actual.data.repos, expected)
            Assert.assertEquals(actual.data.totalCount, 1)
        }

        coVerify {
            sut.getTrendingRepositories("query")
        }
    }

    @Test
    fun `test when remote data not successfully fetch and through an error`() = runTest {

        val sut = mockk<IGithubRepository> {
            coEvery { getTrendingRepositories("query") } returns ApiResponse.Error(mockk {
                coEvery { message } returns "No Internet"
                coEvery { statusCode } returns 504
            })
        }

        val actual = sut.getTrendingRepositories("query")
        val expected = 504

        if (actual is ApiResponse.Error)
            Assert.assertEquals(actual.error.statusCode, expected)

        coVerify {
            sut.getTrendingRepositories("query")
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}