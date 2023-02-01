package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestLogbook(
    val id: String = "",
    val status: Boolean = false,
    val companyId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val logbookDate: String = "",
) : Parcelable {
    companion object {
        fun processStudentRequest(
            id: String,
            status: Boolean,
            companyId: String,
            studentId: String,
            studentName: String,
            logbookDate: String
        ): RequestLogbook {
            return RequestLogbook(
                id,
                status,
                companyId,
                studentId,
                studentName,
                logbookDate
            )
        }
    }
}