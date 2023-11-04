package com.konkuk.capture.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.konkuk.capture.data.search.SearchFoodDataSource
import com.konkuk.capture.databinding.ActivitySearchBinding
import com.konkuk.capture.ui.enroll.EnrollTextInput
import com.konkuk.capture.ui.enroll.EnrollTextInputViewModel
import com.konkuk.common.data.FoodInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFoodActivity : AppCompatActivity() {

    private val viewModel by viewModels<SearchFoodViewModel>()
    private lateinit var adapter: SearchFoodAdapter
    private lateinit var binding: ActivitySearchBinding
    private var scope: CoroutineScope? = null

    @Inject
    lateinit var ser: SearchFoodDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.lifecycleOwner = this
        binding.vm = viewModel

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.foodList.collect { flow ->
                    scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
                    scope?.launch {
                        adapter = SearchFoodAdapter { handleEvent(it) }
                        binding.rvFoodlist.adapter = adapter
                        flow?.collectLatest {
                            adapter.submitData(it)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.textField.collectLatest { text ->
                if (text.isBlank()) {
                    delay(200)
                    binding.llSearchNotiImage.visibility = View.VISIBLE
                } else {
                    binding.llSearchNotiImage.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collectLatest {
                    handleEvent(it)
                }
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun handleEvent(foodInfo: FoodInfo?) {
        startActivity(
            Intent(this@SearchFoodActivity, EnrollTextInput::class.java).apply {
                putExtra(
                    EnrollTextInputViewModel.API_RESULT_KEY,
                    foodInfo,
                )
            },
        )
    }

    override fun onDestroy() {
        scope = null
        super.onDestroy()
    }
}
