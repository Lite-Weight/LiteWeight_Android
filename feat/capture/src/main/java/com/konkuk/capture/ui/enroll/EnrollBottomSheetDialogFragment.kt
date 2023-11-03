package com.konkuk.capture.ui.enroll

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.konkuk.capture.databinding.FragmentBottomSheetEnrollBinding
import com.konkuk.capture.ui.capture.CaptureActivity
import com.konkuk.capture.ui.search.SearchFoodActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EnrollBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetEnrollBinding? = null
    private val binding: FragmentBottomSheetEnrollBinding get() = requireNotNull(_binding)

    private val selection = MutableStateFlow<Boolean?>(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBottomSheetEnrollBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSelector()
    }

    private fun initSelector() = with(binding) {
        clickLeft = {
            selection.value = true
        }

        clickRight = {
            selection.value = false
        }

        btnContinue.setOnClickListener {
            if (selection.value == true) {
                val intent = Intent(context, CaptureActivity::class.java)
                startActivity(intent)
            } else if (selection.value == false) {
                val intent = Intent(context, SearchFoodActivity::class.java)
                startActivity(intent)
            }
            dismiss()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                selection.collectLatest { selected ->
                    selected?.let {
                        btnContinue.isEnabled = true
                        selectorCamera.isSelected = it
                        selectorPen.isSelected = it.not()
                    } ?: kotlin.run {
                        btnContinue.isEnabled = false
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
