package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestStudent(
    val id: String = "",
    val status: String = "",
    val companyId: String = "",
    val companyName : String = "",
    val studentId: String = "",
    val studentName: String = "",
    val studentEmail: String = "",
    val image: String = "",
    val reportStatus: String = "",
    val timestamp: String = "",
) : Parcelable {
    companion object {
        fun processStudentRequest(
            id: String,
            status: String,
            companyId: String,
            companyName: String,
            studentId: String,
            studentName: String,
            studentEmail: String,
            image: String,
            reportStatus: String,
            timestamp: String
        ): RequestStudent {
            return RequestStudent(
                id,
                status,
                companyId,
                companyName,
                studentId,
                studentName,
                studentEmail,
                image,
                reportStatus,
                timestamp
            )
        }
    }
}