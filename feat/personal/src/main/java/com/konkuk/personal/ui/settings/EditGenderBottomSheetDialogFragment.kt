package com.konkuk.personal.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.konkuk.personal.databinding.FragmentEditGenderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditGenderBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEditGenderBinding? = null
    private val binding: FragmentEditGenderBinding get() = requireNotNull(_binding)

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditGenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        initSelector()
        initNext()
    }

    private fun initNext() {
        binding.btnContinue.setOnClickListener {
            viewModel.setGender()
            dismiss()
        }
    }

    private fun initSelector() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.editableGender.collect { isMale ->
                    isMale?.let {
                        selectorMale.isSelected = it
                        selectorFemale.isSelected = it.not()
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
