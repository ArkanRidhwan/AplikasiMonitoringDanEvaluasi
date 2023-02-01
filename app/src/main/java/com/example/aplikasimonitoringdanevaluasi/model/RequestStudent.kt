package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestStudent(
    val id: String = "",
    val status: String = "1",
    val companyId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val studentEmail: String = "",
    val image: String = ""
) : Parcelable {
    companion object {
        fun processStudentRequest(
            id: String,
            status: String,
            companyId: String,
            studentId: String
        ): RequestStudent {
            return RequestStudent(
                id,
                status,
                companyId,
                studentId
            )
        }

        fun processStudentRequest2(
            id: String,
            status: String,
            companyId: String,
            studentId: String,
            studentName: String,
            studentEmail: String,
            image: String
        ): RequestStudent {
            return RequestStudent(
                id,
                status,
                companyId,
                studentId,
                studentName,
                studentEmail,
                image
            )
        }
    }
}