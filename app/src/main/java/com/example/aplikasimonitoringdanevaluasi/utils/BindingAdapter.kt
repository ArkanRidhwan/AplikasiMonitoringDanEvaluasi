package com.example.aplikasimonitoringdanevaluasi.utils

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.aplikasimonitoringdanevaluasi.R

@BindingAdapter("android:loadImageBase64")
fun ImageView.loadImageBase64(base64: String?) {
    if (base64 != null) {
        try {
            val imageBytes = Base64.decode(base64, Base64.DEFAULT)
            val imageDecode = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            Glide.with(context)
                .load(imageDecode)
                .into(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@BindingAdapter("android:setBgCardView")
fun CardView.setBgCardView(senderId: String?) {
    if (senderId != null) {
        try {
            if (getInstance(context).getString(Constant.ID) == senderId) {
                this.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            } else {
                this.setCardBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}