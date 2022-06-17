package pk.sadapay.trendingrepos.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import pk.sadapay.trendingrepos.R
import pk.sadapay.trendingrepos.data.dto.Repo
import pk.sadapay.trendingrepos.databinding.ActivityMainBinding
import pk.sadapay.trendingrepos.ui.adapter.TrendingRepoListAdapter
import pk.sadapay.trendingrepos.utils.UIState
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IMain.View {
    lateinit var viewDataBinding: ActivityMainBinding

    private val viewModel: MainVM by viewModels()

    @Inject
    lateinit var adapter: TrendingRepoListAdapter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewDataBinding.root)
        initRecyclerView(adapter)
        viewModel.loadTopRepositories(refresh = false)

        getView(
            viewDataBinding.multiStateView,
            MultiStateView.ViewState.ERROR,
            R.id.buttonRetry
        )?.setOnClickListener {
            viewModel.loadTopRepositories(refresh = true)
        }

        viewModel.topRepos.observe(this, ::setRepoListToAdapter)
        viewModel.state.observe(this, ::onUiStateChange)
    }

    override fun getView(
        multiStateView: MultiStateView,
        state: MultiStateView.ViewState,
        viewId: Int
    ): View? {
        return multiStateView.getView(state)
            ?.findViewById(viewId)
    }

    override fun onUiStateChange(uiState: UIState) {

        viewDataBinding.multiStateView.viewState = when (uiState) {
            is UIState.Loading -> MultiStateView.ViewState.LOADING
            is UIState.Error -> {

                (getView(
                    viewDataBinding.multiStateView,
                    MultiStateView.ViewState.ERROR,
                    R.id.tvErrorDescription
                ) as MaterialTextView).text = uiState.error

                MultiStateView.ViewState.ERROR
            }
            is UIState.Content -> MultiStateView.ViewState.CONTENT
        }
    }

    override fun initRecyclerView(adapter: TrendingRepoListAdapter) {
        with(viewDataBinding.recyclerView) {
            this.adapter = adapter
        }
    }

    override fun setRepoListToAdapter(list: MutableList<Repo>) {
        adapter.setList(list)
    }
}