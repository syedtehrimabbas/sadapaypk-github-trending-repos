package pk.sadapay.trendingrepos.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import pk.sadapay.trendingrepos.R
import pk.sadapay.trendingrepos.databinding.ActivityMainBinding
import pk.sadapay.trendingrepos.utils.UIState

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IMain.View {
    lateinit var viewDataBinding: ActivityMainBinding

    private val viewModel: MainVM by viewModels()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewDataBinding.root)
        viewModel.loadTopRepositories(refresh = false)

        getView(
            viewDataBinding.multiStateView,
            MultiStateView.ViewState.ERROR,
            R.id.buttonRetry
        )?.setOnClickListener {
            viewModel.loadTopRepositories(refresh = true)
        }

        viewModel.topRepos.observe(this) {
            Log.d("topRepos", it.size.toString())
        }
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
}