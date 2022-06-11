package pk.sadapay.trendingrepos.data.repo.local

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test
import pk.sadapay.trendingrepos.data.dto.Repo

@ExperimentalCoroutinesApi
class LocalGithubRepositoryTest {
    lateinit var sut: ILocalGithubRepository

    @Test
    fun `insert data and get data from database and verify the data is being store in local`() =
        runTest {
            sut = mockk {
                coEvery { insertTopRepos(listOf(Repo(), Repo())) } returns mockk()
                coEvery { getTopRepos() } returns listOf(Repo(), Repo())
            }
            sut.insertTopRepos(listOf(Repo(), Repo()))
            val actual = sut.getTopRepos()
            val expected = listOf(Repo(), Repo())

            Assert.assertEquals(actual, expected)

            coVerify {
                sut.insertTopRepos(listOf(Repo(), Repo()))
                sut.getTopRepos()
            }
        }

    @Test
    fun `insert data and get data from database and verify the data is being store in local fail`() =
        runTest {
            sut = mockk {
                coEvery { insertTopRepos(listOf(Repo())) } returns mockk()
                coEvery { getTopRepos() } returns listOf(Repo(), Repo())
            }
            sut.insertTopRepos(listOf(Repo()))
            val actual = sut.getTopRepos()
            val expected = listOf(Repo(), Repo())

            Assert.assertEquals(actual, expected)

            coVerify {
                sut.insertTopRepos(listOf(Repo()))
                sut.getTopRepos()
            }
        }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}