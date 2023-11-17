package com.konkuk.capture.ui.capture

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.konkuk.capture.databinding.ActivityCaptureBinding
import com.konkuk.capture.ui.enroll.EnrollTextInput
import com.konkuk.capture.ui.enroll.EnrollTextInputViewModel.Companion.BITMAP_PICTURE_KEY
import com.konkuk.capture.ui.enroll.EnrollTextInputViewModel.Companion.OCR_RESULT_KEY
import com.konkuk.capture.ui.enroll.EnrollTextInputViewModel.Companion.URI_PICTURE_KEY
import java.text.SimpleDateFormat
import java.util.Date

class CaptureActivity : AppCompatActivity() {

    private lateinit var captureDialog: CaptureDialogFragment
    private lateinit var binding: ActivityCaptureBinding
    private val recognizer =
        TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    private var bitmapPicture: Bitmap? = null
    private var uriPicture: Uri? = null

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture(),
    ) { isSuccess ->
        if (isSuccess) {
            binding.ivNutritionInfo.setImageURI(uriPicture)
            uriPicture?.let { uri ->
                processImageRecognize(
                    InputImage.fromFilePath(
                        this@CaptureActivity,
                        uri,
                    ),
                )
            }
        }
    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    private val getPictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {
        if (it.resultCode == RESULT_OK) {
            it.data?.data?.let { uri ->
                uriPicture = uri
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
            uriPicture = createImageFile()
            uriPicture?.let { uri -> takePictureLauncher.launch(uri) }
        }, {
            getPictureLauncher.launch(getPictureIntent)
        }, {
            reCapture()
        })
        captureDialog.show(supportFragmentManager, "CaptureDialogFragment")

        binding.llBackBtn.setOnClickListener {
            finish()
            startActivity(Intent(this@CaptureActivity, EnrollTextInput::class.java))
        }
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
                Intent(this@CaptureActivity, EnrollTextInput::class.java).apply {
                    putExtra(OCR_RESULT_KEY, visionText.text)
                    putExtra(BITMAP_PICTURE_KEY, bitmapPicture)
                    putExtra(URI_PICTURE_KEY, uriPicture)
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
