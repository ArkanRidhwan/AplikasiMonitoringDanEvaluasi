package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val id: String = "",
    val tittle: String = "",
    val description: String = "",
    val date: String = "",
    val timestamp: Long = 0,
    val courseId: String = "",
    val link: String = ""
) : Parcelable {
    companion object {
        fun saveVideo(
            id: String,
            tittle: String,
            description: String,
            date: String,
            timestamp: Long,
            courseId: String,
            link: String
        ): Video {
            return Video(id, tittle, description, date, timestamp, courseId, link)
        }
    }
}