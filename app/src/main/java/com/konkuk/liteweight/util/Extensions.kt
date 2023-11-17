package com.konkuk.liteweight.util

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showPermissionContextPopup(permissionName: String) {
    AlertDialog.Builder(this)
        .setTitle("권한 요청")
        .setMessage("해당 기능을 사용하기 위해서는\n $permissionName 권한이 필요합니다.")
        .setPositiveButton("동의하기") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        }
        .setNegativeButton("취소하기") { _, _ -> }
        .create()
        .show()
}
