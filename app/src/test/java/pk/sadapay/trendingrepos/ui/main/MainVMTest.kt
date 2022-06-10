package pk.sadapay.trendingrepos.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import org.junit.*

@ExperimentalCoroutinesApi
class MainVMTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var sut: MainVM

    @Before
    fun setUpMainViewModel() {
        sut = MainVM()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() //reset
    }

    @Test
    fun testLoadTopRepositoriesApiSuccess() {
        sut.loadTopRepositories(refresh = true)
        Assert.assertNull(sut.topRepos.value)
    }

    @Test
    fun testLoadTopRepositoriesApiFail() {
        sut.loadTopRepositories(refresh = true)
        Assert.assertNull(sut.topRepos.value)
    }

}