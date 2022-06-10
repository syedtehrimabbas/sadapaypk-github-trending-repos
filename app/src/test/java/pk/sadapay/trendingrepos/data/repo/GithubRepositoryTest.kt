package pk.sadapay.trendingrepos.data.repo

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pk.sadapay.trendingrepos.common.Utils
import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.dto.GithubTrendingRepos
import pk.sadapay.trendingrepos.data.repo.local.ILocalGithubRepository
import pk.sadapay.trendingrepos.data.repo.remote.IRemoteGithubRepository

@ExperimentalCoroutinesApi
class GithubRepositoryTest {

    private val remoteGithubRepository: IRemoteGithubRepository = mockk()
    private val localGithubRepository: ILocalGithubRepository = mockk()
    lateinit var sut: GithubRepository

    @Before
    fun setUp() {
        sut = GithubRepository(localGithubRepository, remoteGithubRepository)
    }

    @Test
    fun `check the data is being returned either it is stale or remote`() {
        val res = getMockResponse()
        coEvery { localGithubRepository.getTopRepos() } returns res.repos!!
        coEvery { remoteGithubRepository.getTrendingRepositories("abc") } returns ApiResponse.Success(200, res)
        runBlocking {
            Assert.assertNotNull(sut.loadGithubTopRepositories("abc", false))
        }
    }

    private fun getMockResponse(): GithubTrendingRepos {
        val gson = GsonBuilder().create()
        val itemType = object : TypeToken<GithubTrendingRepos>() {}.type
        return gson.fromJson(Utils.readFileFromTestResources("success_response_api.json"), itemType)
    }

    @After
    fun tearDown() {
    }
}