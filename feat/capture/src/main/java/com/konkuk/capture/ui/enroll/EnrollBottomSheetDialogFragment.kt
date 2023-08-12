package com.konkuk.capture.ui.enroll

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.konkuk.capture.databinding.FragmentBottomSheetEnrollBinding
import com.konkuk.capture.ui.capture.CaptureActivity
import com.konkuk.capture.ui.search.SearchFoodActivity

class EnrollBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetEnrollBinding? = null
    private val binding: FragmentBottomSheetEnrollBinding get() = requireNotNull(_binding)

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

        binding.lifecycleOwner = viewLifecycleOwner

        initSelector()
        initNext()
    }

    private fun initNext() {
        binding.btnContinue.setOnClickListener {
            dismiss()
            if (binding.selectorCamera.isSelected) {
                val intent = Intent(context, CaptureActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, SearchFoodActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initSelector() = with(binding) {
        selectorCamera.isSelected = true
        selectorPen.isSelected = false
        selectorCamera.setOnClickListener {
            selectorCamera.isSelected = true
            selectorPen.isSelected = false
        }
        selectorPen.setOnClickListener {
            selectorCamera.isSelected = false
            selectorPen.isSelected = true
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
