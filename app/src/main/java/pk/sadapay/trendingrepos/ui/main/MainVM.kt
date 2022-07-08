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

    var originalTopRepos: List<Repo> = listOf()

    override fun deleteCache() {
        cache.evictAll()
    }

    override fun loadTopRepositories(queryParam: String, refresh: Boolean) {
        if (refresh)
            deleteCache()
        launch {
            _state.postValue(UIState.Loading)
            when (val response = githubRepository.getTrendingRepositories(queryParam)) {
                is ApiResponse.Success -> {
                    _topRepos.postValue(response.data.repos as MutableList<Repo>?)
                    _state.postValue(UIState.Content)
                }
                is ApiResponse.Error -> {
                    _topRepos.postValue(mutableListOf())
                    _state.postValue(UIState.Error(response.error.message))
                }
            }
        }
    }

    private fun launch(dispatcher: CoroutineContext = Dispatchers.IO, block: suspend () -> Unit) {
        viewModelScope.launch(dispatcher) { block() }
    }

    override fun sortDataAlphabetic() {
        _topRepos.value = originalTopRepos.sortedBy { it.name } as MutableList<Repo>
    }

    override fun unSortData() {
        _topRepos.value = originalTopRepos as MutableList<Repo>
    }
}