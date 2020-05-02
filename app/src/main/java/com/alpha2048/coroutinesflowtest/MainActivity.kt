package com.alpha2048.coroutinesflowtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alpha2048.coroutinesflowtest.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    //private val viewModel: MainViewModel by viewModels()

    private val viewModel: MainViewModel by inject()

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.progress.visibility = View.VISIBLE
        viewModel.loadData()

        binding.recyclerView.adapter = MainAdapter(viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.trigger
                .asFlow()
                .flowOn(Dispatchers.Default)
                .collect {
                    binding.progress.visibility = View.INVISIBLE
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }
        }
    }
}
