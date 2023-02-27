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
    val reportStatus: String = ""
) : Parcelable {
    companion object {
        /*fun processStudentRequest(
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
        }*/

        fun processStudentRequest(
            id: String,
            status: String,
            companyId: String,
            companyName: String,
            studentId: String,
            studentName: String,
            studentEmail: String,
            image: String,
            reportStatus: String
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
                reportStatus
            )
        }
    }
}