package com.konkuk.capture.ui.enroll

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("capturedPicture")
fun ImageView.capturedPicture(capturedPicture: CapturedPicture) {
    println("capturedPicture $capturedPicture")
    when (capturedPicture) {
        is CapturedPicture.BitmapPicture -> setImageBitmap(capturedPicture.bitmap)
        is CapturedPicture.UriPicture -> setImageURI(capturedPicture.uri)
        else -> {}
    }
}
