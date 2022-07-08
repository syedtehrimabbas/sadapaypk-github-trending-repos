package pk.sadapay.trendingrepos.ui.main

import androidx.lifecycle.LiveData
import com.kennyc.view.MultiStateView
import pk.sadapay.trendingrepos.data.dto.Repo
import pk.sadapay.trendingrepos.ui.adapter.TrendingRepoListAdapter
import pk.sadapay.trendingrepos.utils.UIState

interface IMain {
    interface View {
        fun getView(
            multiStateView: MultiStateView,
            state: MultiStateView.ViewState,
            viewId: Int
        ): android.view.View?

        fun onUiStateChange(uiState: UIState)
        fun initRecyclerView(adapter: TrendingRepoListAdapter)
        fun setRepoListToAdapter(list: MutableList<Repo>)
    }

    interface ViewModel {
        val topRepos: LiveData<MutableList<Repo>>
        fun loadTopRepositories(queryParam: String = "language=+sort:stars", refresh: Boolean)
        var state: LiveData<UIState>
        fun deleteCache()
        fun sortDataAlphabetic()
        fun unSortData()
    }
}