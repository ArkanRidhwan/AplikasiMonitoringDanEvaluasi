package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Module(
    val id: String = "",
    val tittle: String= "",
    val description: String = "",
    val date: String = "",
    val timestamp: String = "",
    val courseId: String = "",
    val link: String = ""
): Parcelable {
    companion object {
        fun saveModule(
            id: String,
            tittle: String,
            description: String,
            date: String,
            timestamp: String,
            courseId: String,
            link: String
        ): Module {
            return Module(id, tittle, description, date, timestamp, courseId, link)
        }
    }
}