package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Report(
    val id: String = "",
    val companyId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val tittle: String = "",
    val date: String = "",
    val timestamp: String = "",
    val answer1: String = "",
    val answer2: String = "",
    val answer3: String = "",
    val answer4: String = "",
    val answer5: String = "",
    val answer6: String = "",
    val answer7: String = "",
    val answer8: String = "",
    val answer9: String = "",
    val answer10: String = "",
    val answer11: String = "",
    val answer12: String = "",
    val answer13: String = "",
    val answer14: String = "",
    val answer15: String = "",
    val answer16: String = "",
    val answer17: String = "",
    val answer18: String = ""
) : Parcelable {
    companion object {
        fun saveReport(
            id: String,
            companyId: String,
            studentId: String,
            studentName: String,
            tittle: String,
            date: String,
            timestamp: String,
            answer1: String,
            answer2: String,
            answer3: String,
            answer4: String,
            answer5: String,
            answer6: String,
            answer7: String,
            answer8: String,
            answer9: String,
            answer10: String,
            answer11: String,
            answer12: String,
            answer13: String,
            answer14: String,
            answer15: String,
            answer16: String,
            answer17: String,
            answer18: String,
        ): Report {
            return Report(
                id,
                companyId,
                studentId,
                studentName,
                tittle,
                date,
                timestamp,
                answer1,
                answer2,
                answer3,
                answer4,
                answer5,
                answer6,
                answer7,
                answer8,
                answer9,
                answer10,
                answer11,
                answer12,
                answer13,
                answer14,
                answer15,
                answer16,
                answer17,
                answer18
            )
        }
    }
}