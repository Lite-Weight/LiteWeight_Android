package com.konkuk.capture.ui.enroll

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.konkuk.capture.databinding.FragmentBottomSheetEnrollBinding

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

        // 등록방법 선택
        initSelector()
        // 다음화면 넘어감
        initNext()
    }

    private fun initNext() {
        binding.btnContinue.setOnClickListener {
            if (binding.selectorCamera.isSelected) {
                val intent = Intent(context, EnrollCameraInput::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, EnrollTextInput::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initSelector() = with(binding) {
        // 카메라 혹은 펜 둘중 하나 선택 하는 버튼(카메라가 기본 선택)
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
