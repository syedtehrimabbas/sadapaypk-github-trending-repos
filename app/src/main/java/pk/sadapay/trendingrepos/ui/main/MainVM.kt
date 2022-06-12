package pk.sadapay.trendingrepos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Cache
import pk.sadapay.trendingrepos.data.base.ApiResponse
import pk.sadapay.trendingrepos.data.dto.Repo
import pk.sadapay.trendingrepos.data.repo.IGithubRepository
import pk.sadapay.trendingrepos.utils.UIState
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainVM @Inject constructor(
    private val githubRepository: IGithubRepository,
    private val cache: Cache
) :
    ViewModel(),
    IMain.ViewModel {

    private val _topRepos: MutableLiveData<MutableList<Repo>> = MutableLiveData()
    override val topRepos: LiveData<MutableList<Repo>> = _topRepos

    private var _state: MutableLiveData<UIState> = MutableLiveData()
    override var state: LiveData<UIState> = _state

    override fun deleteCache(cacheDir: File): Boolean {
        return when {
            cacheDir.isDirectory -> {
                val children: Array<String> = cacheDir.list() as Array<String>
                for (i in children.indices) {
                    val success = deleteCache(File(cacheDir, children[i]))
                    if (!success) {
                        return false
                    }
                }
                cacheDir.delete()
            }
            cacheDir.isFile -> {
                cacheDir.delete()
            }
            else -> {
                false
            }
        }
    }

    override fun loadTopRepositories(queryParam: String, refresh: Boolean) {
        launch {
            if (refresh)
                deleteCache(cacheDir = cache.directory)
            _state.postValue(UIState.Loading)
            when (val response = githubRepository.getTrendingRepositories(queryParam)) {
                    is ApiResponse.Success -> {
                        _topRepos.value = response.data.repos as MutableList<Repo>?
                        _state.value = UIState.Content
                    }
                    is ApiResponse.Error -> {
                        _topRepos.value = mutableListOf()
                        _state.value = UIState.Error(response.error.message)
                    }
                }
        }
    }

    private fun launch(dispatcher: CoroutineContext = Dispatchers.IO, block: suspend () -> Unit) {
        viewModelScope.launch(dispatcher) { block() }
    }
}