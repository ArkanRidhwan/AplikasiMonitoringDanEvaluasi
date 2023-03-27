package com.example.aplikasimonitoringdanevaluasi.utils

import android.annotation.SuppressLint
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
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

private lateinit var preferenceManager: PreferenceManager

@Synchronized


fun encrypt(strToEncrypt: String): String? {
    val secretKey = "tK5UTui+DPh8lIlBxya5XVsmeDCoUl6vHhdIESMB6sQ="
    val iv = "bVQzNFNhRkQ1Njc4UUFaWA==" // base64 decode => mT34SaFD5678QAZX
    val salt = "QWlGNHNhMTJTQWZ2bGhpV3U="
    try {
        val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec =
            PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.DEFAULT), 10000, 256)
        val tmp = factory.generateSecret(spec)
        val secretKey = SecretKeySpec(tmp.encoded, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
        return Base64.encodeToString(
            cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)),
            Base64.DEFAULT
        )
    } catch (e: Exception) {
        println("Error while encrypting: $e")
    }
    return null
}

fun decrypt(strToDecrypt: String): String? {
    val secretKey = "tK5UTui+DPh8lIlBxya5XVsmeDCoUl6vHhdIESMB6sQ="
    val iv = "bVQzNFNhRkQ1Njc4UUFaWA==" // base64 decode => mT34SaFD5678QAZX
    val salt = "QWlGNHNhMTJTQWZ2bGhpV3U=" // base64 decode => AiF4sa12SAfvlhiWu
    try {

        val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec =
            PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.DEFAULT), 10000, 256)
        val tmp = factory.generateSecret(spec);
        val secretKey = SecretKeySpec(tmp.encoded, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
    } catch (e: Exception) {
        println("Error while decrypting: $e");
    }
    return null
}


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

@SuppressLint("SimpleDateFormat")
fun getDateNow(): String {
    val sdf = SimpleDateFormat("EEE, d MMM yyyy ")
    val currentDate = sdf.format(Date())
    return currentDate
}

/*fun getTimeStamp(): String {
    val sdf = SimpleDateFormat("EEE, d MMM yyyy ")
    val currentDate = sdf.format(Date())
    return currentDate
}*/

fun ImageView.loadCircleImageFromUrl(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun Bitmap.encodeImage(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val b = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun ImageView.uploadImage(file: File?): UploadTask {
    val storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("images/${file?.name}")

    val bitmapImage = (drawable as BitmapDrawable).bitmap
    val baos = ByteArrayOutputStream()
    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val dataBaos = baos.toByteArray()
    return imagesRef.putBytes(dataBaos)
}