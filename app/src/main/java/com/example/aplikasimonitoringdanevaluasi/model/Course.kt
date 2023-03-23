package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Course(
    val id: String = "",
    val adminId: String = "",
    val studentId: String = "",
    val tittle: String = "",
    val number: String = "",
    val timestamp: Long = 0,
    val check1: Boolean = false,
    val check2: Boolean = false,
    val check3: Boolean =false
) : Parcelable {
    companion object {
        fun saveCourse(
            id: String,
            adminId: String,
            studentId: String,
            tittle: String,
            number: String,
            timestamp: Long,
            check1: Boolean,
            check2: Boolean,
            check3: Boolean
        ): Course {
            return Course(
                id,
                adminId,
                studentId,
                tittle,
                number,
                timestamp,
                check1,
                check2,
                check3
            )
        }
    }
}