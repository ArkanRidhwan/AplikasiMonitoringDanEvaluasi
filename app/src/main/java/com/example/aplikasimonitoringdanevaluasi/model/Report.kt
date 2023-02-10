package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Report(
    val id: String = "",
    val companyId: String = "",
    val studentId: String = "",
    val tittle: String = "",
    val date: String = "",
    val answer1: String = "",
    val answer2: String = "",
    val answer3: String = "",
    val answer4: String = "",
    val answer5: String = "",
    val answer6: String = "",
    val answer7: String = "",
    val answer8: String = ""
) : Parcelable {
    companion object {
        fun saveReport(
            id: String,
            companyId: String,
            studentId: String,
            tittle: String,
            date: String,
            answer1: String,
            answer2: String,
            answer3: String,
            answer4: String,
            answer5: String,
            answer6: String,
            answer7: String,
            answer8: String
        ): Report {
            return Report(
                id,
                companyId,
                studentId,
                tittle,
                date,
                answer1,
                answer2,
                answer3,
                answer4,
                answer5,
                answer6,
                answer7,
                answer8
            )
        }
    }
}