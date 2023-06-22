package com.konkuk.capture.ui.capture

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.capture.databinding.ActivityCaptureBinding

class CaptureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaptureBinding

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {
        if (it.resultCode == RESULT_OK) {
            binding.ivNutritionInfo.setImageBitmap(it.data?.extras?.get("data") as Bitmap) // data?.data?.let { uri ->
            // Log.d("Capture", uri.toString())
        }
    }

    private val getPictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {
        if (it.resultCode == RESULT_OK) {
            binding.ivNutritionInfo.setImageURI(it.data?.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaptureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCapture()
    }

    private fun initCapture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val getPictureIntent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }

        val dialog = CaptureDialogFragment({
            takePictureLauncher.launch(takePictureIntent)
        }, {
            getPictureLauncher.launch(getPictureIntent)
        })
        dialog.show(supportFragmentManager, "CaptureDialogFragment")
    }
}
