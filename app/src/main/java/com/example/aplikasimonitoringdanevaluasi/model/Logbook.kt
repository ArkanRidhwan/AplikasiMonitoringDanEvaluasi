package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Logbook(
    val id: String = "",
    val logbookUserId: String = "",
    val companyId: String = "",
    val name: String = "",
    val content: String = "",
    val date: String = "",
    val status: Boolean = false
) : Parcelable {
    companion object {
        fun saveLogbook(
            id: String,
            logbookUserId: String,
            companyId: String,
            name: String,
            content: String,
            date: String,
            status: Boolean
        ): Logbook {
            return Logbook(id, logbookUserId, companyId, name, content, date, status)
        }
    }
}