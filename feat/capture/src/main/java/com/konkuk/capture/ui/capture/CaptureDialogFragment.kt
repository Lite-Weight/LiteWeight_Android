package com.konkuk.capture.ui.capture

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CaptureDialogFragment(
    private val onTakePicture: () -> Unit,
    private val onGetPicture: () -> Unit,
    private val onDisMiss: () -> Unit,
) : DialogFragment() {

    private val captureOptions = arrayOf("직접 찍기", "가져오기")

    @SuppressLint("DialogFragmentCallbacksDetector")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
                .setItems(
                    captureOptions,
                ) { dialog, index ->
                    when (index) {
                        0 -> onTakePicture()
                        1 -> onGetPicture()
                        else -> dialog.cancel()
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDisMiss()
        super.onDismiss(dialog)
    }
}
