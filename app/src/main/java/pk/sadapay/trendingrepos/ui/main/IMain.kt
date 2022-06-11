package pk.sadapay.trendingrepos.ui.main

import androidx.lifecycle.LiveData
import pk.sadapay.trendingrepos.data.dto.Repo
import pk.sadapay.trendingrepos.utils.UIState
import java.io.File

interface IMain {
    interface View {
    }

    interface ViewModel {
        val topRepos: LiveData<MutableList<Repo>>
        fun loadTopRepositories(queryParam: String = "language=+sort:stars", refresh: Boolean)
        fun reloadData()
        var state: LiveData<UIState>
        fun deleteCache(cacheDir: File) :Boolean
    }
}