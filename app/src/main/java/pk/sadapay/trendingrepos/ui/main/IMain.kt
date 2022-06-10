package pk.sadapay.trendingrepos.ui.main

import androidx.lifecycle.LiveData
import pk.sadapay.trendingrepos.data.dto.Repo

interface IMain {
    interface View {
    }

    interface ViewModel {
        val topRepos: LiveData<MutableList<Repo>>
        fun loadTopRepositories(queryParam: String = "language=+sort:stars", refresh: Boolean)
        fun reloadData()
    }
}