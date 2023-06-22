package com.konkuk.capture.ui.capture

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.konkuk.capture.databinding.ActivityCaptureBinding
import com.konkuk.capture.ui.result.CaptureResultActivity
import com.konkuk.capture.ui.result.CaptureResultViewModel.Companion.NAME_KEY
import com.konkuk.capture.ui.result.CaptureResultViewModel.Companion.OCR_RESULT_KEY

class CaptureActivity : AppCompatActivity() {

    private lateinit var captureDialog: CaptureDialogFragment
    private lateinit var binding: ActivityCaptureBinding
    private val recognizer =
        TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {
        if (it.resultCode == RESULT_OK) {
            (it.data?.extras?.get("data") as Bitmap?)?.let { bitmap ->
                binding.ivNutritionInfo.setImageBitmap(bitmap)
                processImageRecognize(InputImage.fromBitmap(bitmap, 0))
            } ?: reCapture()
        } else {
            reCapture()
        }
    }

    private val getPictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {
        if (it.resultCode == RESULT_OK) {
            it.data?.data?.let { uri ->
                binding.ivNutritionInfo.setImageURI(uri)
                processImageRecognize(InputImage.fromFilePath(this@CaptureActivity, uri))
            } ?: reCapture()
        } else {
            reCapture()
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

        captureDialog = CaptureDialogFragment({
            takePictureLauncher.launch(takePictureIntent)
        }, {
            getPictureLauncher.launch(getPictureIntent)
        })
        captureDialog.show(supportFragmentManager, "CaptureDialogFragment")
    }

    @SuppressLint("SetTextI18n")
    private fun processImageRecognize(image: InputImage) = with(binding) {
        tvProgressTitle.text = "처리중"
        recognizer.process(image).addOnSuccessListener { visionText ->
            doneCapture(visionText)
        }.addOnFailureListener { _ ->
            reCapture()
        }
    }

    private fun doneCapture(visionText: Text) = with(binding) {
        tvProgressTitle.text = "결과 보러가기"
        tvProgressContent.text = "move to next page"
        tvProgress.setOnClickListener {
            finish()
            startActivity(
                Intent(this@CaptureActivity, CaptureResultActivity::class.java).apply {
                    putExtra(
                        OCR_RESULT_KEY,
                        visionText.text,
                    )
                    putExtra(
                        NAME_KEY,
                        intent.getStringExtra(NAME_KEY),
                    )
                },
            )
        }
    }

    private fun reCapture() = with(binding) {
        tvProgressTitle.text = "사진을 다시 찍어주세요"
        tvProgressContent.text = "fail task"
        tvProgress.setOnClickListener {
            captureDialog.show(supportFragmentManager, "CaptureDialogFragment")
        }
    }
}
