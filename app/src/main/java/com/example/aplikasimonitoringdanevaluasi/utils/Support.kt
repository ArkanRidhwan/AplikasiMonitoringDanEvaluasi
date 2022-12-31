package com.example.aplikasimonitoringdanevaluasi.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File

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

fun ImageView.loadCircleImageFromUri(uri: Uri?) {
    Glide.with(context)
        .load(uri)
        .into(this)
}

fun Bitmap.encodeImage(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val b = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun ImageView.uploadImage(file: File): UploadTask {
    val storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("images/${file.name}")

    val bitmapImage = (drawable as BitmapDrawable).bitmap
    val baos = ByteArrayOutputStream()
    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val dataBaos = baos.toByteArray()
    return imagesRef.putBytes(dataBaos)
}