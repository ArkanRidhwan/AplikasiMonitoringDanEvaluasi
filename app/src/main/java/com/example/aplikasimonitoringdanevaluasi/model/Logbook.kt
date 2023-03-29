package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Logbook(
    val id: String = "",
    val logbookUserId: String = "",
    val companyId: String = "",
    val name: String = "",
    val activityDate: String = "",
    val content: String = "",
    val date: String = "",
    val timestamp: String = "",
    val status: String = "",
    val image: String = " "
) : Parcelable {
    companion object {
        fun saveLogbook(
            id: String,
            logbookUserId: String,
            companyId: String,
            name: String,
            activityDate: String,
            content: String,
            date: String,
            timestamp: String,
            status: String,
            image: String
        ): Logbook {
            return Logbook(
                id,
                logbookUserId,
                companyId,
                name,
                activityDate,
                content,
                date,
                timestamp,
                status,
                image
            )
        }
    }
}