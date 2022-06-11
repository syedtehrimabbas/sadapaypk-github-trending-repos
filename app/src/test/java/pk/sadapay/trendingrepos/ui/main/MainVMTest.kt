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
                    every { repos } returns listOf()
                })
        }
        val mockCache = mockk<Cache>()

        sut = MainVM(mockkApi, mockCache)
        sut.loadTopRepositories("query", false)

        Assert.assertEquals(sut.topRepos.value, listOf<Repo>())

        coVerify {
            mockkApi.getTrendingRepositories("query")
        }
    }

    @Test
    fun `Test Load Top Repositories Api Fail`() {
        val mockkApi = mockk<IGithubRepository> {
            coEvery { getTrendingRepositories("query") } returns ApiResponse.Error(mockk())
        }

        sut = MainVM(mockkApi, mockk())

        sut.loadTopRepositories("query", false)

        Assert.assertEquals(UIState.Error(""), sut.state.value)

        coVerify {
            mockkApi.getTrendingRepositories("query")
        }
    }

    @Test
    fun `Test the user required force current data and cache deleted`() = runTest {
        val mockkApi = mockk<IGithubRepository> {
            coEvery { getTrendingRepositories("query") } returns ApiResponse.Success(
                200,
                mockk {
                    every { repos } returns listOf()
                })
        }
        val mockCache = mockk<Cache>()

        sut = MainVM(mockkApi, mockCache)
        sut.loadTopRepositories("query", true)

        Assert.assertEquals(sut.topRepos.value, listOf<Repo>())

        coVerify {
            mockkApi.getTrendingRepositories("query")
        }
    }

    @After
    fun cleanUp() {
        clearAllMocks()
    }
}