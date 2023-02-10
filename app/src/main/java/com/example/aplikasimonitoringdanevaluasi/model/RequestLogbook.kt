package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestLogbook(
    val id: String = "",
    val status: String = "",
    val companyId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val logbookDate: String = "",
    val content: String = "",
) : Parcelable {
    companion object {
        fun processStudentRequest(
            id: String,
            status: String,
            companyId: String,
            studentId: String,
            studentName: String,
            logbookDate: String,
            content: String
        ): RequestLogbook {
            return RequestLogbook(
                id,
                status,
                companyId,
                studentId,
                studentName,
                logbookDate,
                content
            )
        }
    }
}