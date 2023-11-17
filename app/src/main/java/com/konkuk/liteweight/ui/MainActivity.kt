package com.konkuk.liteweight.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.konkuk.capture.ui.enroll.EnrollBottomSheetDialogFragment
import com.konkuk.liteweight.R
import com.konkuk.liteweight.databinding.ActivityMainBinding
import com.konkuk.liteweight.util.showPermissionContextPopup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                navigateCaptureDialog()
            } else {
                showPermissionContextPopup(Manifest.permission.CAMERA)
            }
        }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        initViews()
        observeEvent()
    }

    private fun initViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun observeEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            Event.CaptureClickEvent -> {
                requestCameraTask()
            }
        }
    }

    private fun navigateCaptureDialog() {
        val bottomSheetDialogFragment = EnrollBottomSheetDialogFragment()
        bottomSheetDialogFragment.show(
            supportFragmentManager,
            bottomSheetDialogFragment.tag,
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun requestCameraTask() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED -> {
                navigateCaptureDialog()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showPermissionContextPopup("카메라")
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}
