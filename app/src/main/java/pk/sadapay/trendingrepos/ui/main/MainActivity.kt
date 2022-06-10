package pk.sadapay.trendingrepos.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import pk.sadapay.trendingrepos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewDataBinding: ActivityMainBinding

    private val viewModel: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewDataBinding.root)
    }
}