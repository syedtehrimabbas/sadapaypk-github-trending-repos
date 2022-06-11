package pk.sadapay.trendingrepos.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import pk.sadapay.trendingrepos.common.CoroutineRule
import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.dto.Repo
import pk.sadapay.trendingrepos.data.repo.IGithubRepository

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
            coEvery { loadGithubTopRepositories("query", false) } returns ApiResponse.Success(
                200,
                mockk {
                    every { repos } returns listOf()
                })
        }

        sut = MainVM(mockkApi)
        sut.loadTopRepositories("query", false)

        Assert.assertEquals(sut.topRepos.value, listOf<Repo>())

        coVerify {
            mockkApi.loadGithubTopRepositories("query", false)
        }
    }

    @Test
    fun testLoadTopRepositoriesApiFail() {

    }

    @After
    fun cleanUp() {
        clearAllMocks()
    }
}