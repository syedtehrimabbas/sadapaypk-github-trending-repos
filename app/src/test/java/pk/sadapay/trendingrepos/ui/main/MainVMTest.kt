package pk.sadapay.trendingrepos.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import org.junit.*
import pk.sadapay.trendingrepos.data.MockGithubRepository
import pk.sadapay.trendingrepos.data.repo.IGithubRepository

@ExperimentalCoroutinesApi
class MainVMTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: MainVM
    lateinit var mockGithubRepository: IGithubRepository

    @Before
    fun setUpMainViewModel() {
        mockGithubRepository = MockGithubRepository()
        sut = MainVM(mockGithubRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() //reset
    }

    @Test
    fun testLoadTopRepositoriesApiSuccess() {
        sut.loadTopRepositories(refresh = true)
        Assert.assertNotNull(sut.topRepos.value)
    }

    @Test
    fun testLoadTopRepositoriesApiFail() {
        sut.loadTopRepositories(refresh = true)
        Assert.assertNull(sut.topRepos.value)
    }

}