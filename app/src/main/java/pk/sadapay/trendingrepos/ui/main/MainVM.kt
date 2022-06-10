package pk.sadapay.trendingrepos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pk.sadapay.trendingrepos.data.dto.Repo

class MainVM : ViewModel(), IMain.ViewModel {

    private val _topRepos: MutableLiveData<MutableList<Repo>> = MutableLiveData()
    override val topRepos: LiveData<MutableList<Repo>> = _topRepos

    override fun loadTopRepositories(queryParam: String, refresh: Boolean) {
    }

    override fun reloadData() {
    }
}