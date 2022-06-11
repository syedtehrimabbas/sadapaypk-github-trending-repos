package pk.sadapay.trendingrepos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pk.sadapay.trendingrepos.data.dto.Repo
import pk.sadapay.trendingrepos.data.repo.IGithubRepository
import pk.sadapay.trendingrepos.utils.UIState

class MainVM constructor(val githubRepository: IGithubRepository) : ViewModel(),
    IMain.ViewModel {

    private val _topRepos: MutableLiveData<MutableList<Repo>> = MutableLiveData()
    override val topRepos: LiveData<MutableList<Repo>> = _topRepos

    private var _state: MutableLiveData<UIState> = MutableLiveData()
    override var state: LiveData<UIState> = _state

    override fun loadTopRepositories(queryParam: String, refresh: Boolean) {
        _topRepos.value = mutableListOf()
    }

    override fun reloadData() {
    }
}