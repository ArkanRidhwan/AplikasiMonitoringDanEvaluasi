package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Report(
    val id: String = "",
    val tittle: String= "",
    val description: String = "",
    val date: String = "",
    val link: String = ""
): Parcelable {
    companion object {
        fun saveVideo(
            id: String,
            tittle: String,
            description: String,
            date: String,
            link: String
        ): Video {
            return Video(id, tittle, description, date, link)
        }
    }
}