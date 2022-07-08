package pk.sadapay.trendingrepos.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.Cache
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import pk.sadapay.trendingrepos.common.CoroutineRule
import pk.sadapay.trendingrepos.common.getOrAwaitValue
import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.dto.Repo
import pk.sadapay.trendingrepos.data.repo.IGithubRepository
import pk.sadapay.trendingrepos.utils.UIState
import java.io.File

@ExperimentalCoroutinesApi
class MainVMTest {
    @Rule
    @JvmField
    var coroutineRule = CoroutineRule()

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: MainVM

    @Test
    fun `test load top repositories api success`() = runTest {
        val mockkApi = mockk<IGithubRepository> {
            coEvery { getTrendingRepositories("query") } returns ApiResponse.Success(
                200,
                mockk {
                    coEvery { repos } returns listOf(Repo())
                })
        }
        val mockCache = mockk<Cache>()

        sut = MainVM(mockkApi, mockCache)
        sut.loadTopRepositories("query", false)

        Assert.assertEquals(listOf(Repo()), sut.originalTopRepos)

        Assert.assertEquals(UIState.Content, sut.state.getOrAwaitValue())

        coVerify {
            mockkApi.getTrendingRepositories("query")
        }
    }

    @Test
    fun `Test Load Top Repositories Api Fail`() {
        val mockkApi = mockk<IGithubRepository> {
            coEvery { getTrendingRepositories("query") } returns ApiResponse.Error(
                mockk {
                    coEvery { message } returns "Error"
                }
            )
        }
        val mockCache = mockk<Cache>()

        sut = MainVM(mockkApi, mockCache)

        sut.loadTopRepositories("query", false)

        Assert.assertEquals(listOf<Repo>(), sut.originalTopRepos)

        coVerify {
            mockkApi.getTrendingRepositories("query")
        }
    }

    @Test
    fun `Test the user required force current data and cache deleted`() = runTest {
        val diskCacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val mockkApi = mockk<IGithubRepository> {
            coEvery { getTrendingRepositories("query") } returns ApiResponse.Success(
                200,
                mockk {
                    every { repos } returns listOf(Repo())
                })
        }
        val cacheDir = File("./build/tmp/test-ok-cache")
        val mockCache = Cache(cacheDir, diskCacheSize)

        sut = MainVM(mockkApi, mockCache)
        sut.loadTopRepositories("query", true)

        Assert.assertEquals(listOf(Repo()), sut.topRepos.getOrAwaitValue())
        Assert.assertEquals(UIState.Content, sut.state.getOrAwaitValue())

        coVerify {
            mockkApi.getTrendingRepositories("query")
        }
    }

    @Test
    fun testIsListSorted() = runTest {
        sut = MainVM(mockk(), mockk())

        sut.originalTopRepos = mutableListOf(
            Repo(name = "D"),
            Repo(name = "C"),
            Repo(name = "B"),
            Repo(name = "A")
        )

        sut.sortDataAlphabetic()

        val expected = mutableListOf(
            Repo(name = "A"),
            Repo(name = "B"),
            Repo(name = "C"),
            Repo(name = "D")
        )

        Assert.assertEquals(expected, sut.topRepos.getOrAwaitValue())
    }
    @Test
    fun testUnSortData() = runTest {
        sut = MainVM(mockk(), mockk())

        sut.originalTopRepos = mutableListOf(
            Repo(name = "D"),
            Repo(name = "C"),
            Repo(name = "B"),
            Repo(name = "A")
        )

        sut.unSortData()

        val expected = mutableListOf(
            Repo(name = "D"),
            Repo(name = "C"),
            Repo(name = "B"),
            Repo(name = "A")
        )

        Assert.assertEquals(expected, sut.topRepos.getOrAwaitValue())
    }

    @After
    fun cleanUp() {
        clearAllMocks()
    }
}