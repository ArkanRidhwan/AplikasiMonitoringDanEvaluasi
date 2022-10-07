package com.example.aplikasimonitoringdanevaluasi.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.io.ByteArrayOutputStream

private lateinit var preferenceManager: PreferenceManager

@Synchronized
fun getInstance(context: Context): PreferenceManager {
    preferenceManager = PreferenceManager(context)
    return preferenceManager
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun EditText.error(text: String) {
    error = text
    requestFocus()
}

// Compress Size
fun encodeImage(bm: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val b = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}