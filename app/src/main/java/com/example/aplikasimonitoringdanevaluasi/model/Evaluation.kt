package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Evaluation(
    val id: String = "",
    val timestamp: String = "",
    val courseId: String = "",
    val link: String = ""
) : Parcelable {
    companion object {
        fun saveEvaluation(
            id: String,
            timestamp: String,
            courseId: String,
            link: String
        ): Evaluation {
            return Evaluation(id, timestamp, courseId, link)
        }
    }
}