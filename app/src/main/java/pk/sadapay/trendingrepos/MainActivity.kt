package pk.sadapay.trendingrepos

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import pk.sadapay.trendingrepos.databinding.ActivityMainBinding
import pk.sadapay.trendingrepos.viewmodel.MainVM

class MainActivity : AppCompatActivity() {
    lateinit var viewDataBinding: ActivityMainBinding
    private val viewModel: MainVM by viewModels()
    private val bindingVariable: Int = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            viewDataBinding = it
            viewDataBinding.lifecycleOwner = this
            viewDataBinding.setVariable(bindingVariable, viewModel)
            viewDataBinding.executePendingBindings()
        }
    }
}