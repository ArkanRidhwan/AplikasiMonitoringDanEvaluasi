package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    val id: String = "",
    val contactEmail: String = "",
    val password: String = "",
    val companyName: String = "",
    val companyAddress: String = "",
    val contactName: String = "",
    val contactPhoneNumber: String = "",
    val image: String = "",
) : Parcelable {
    companion object {
        fun saveRegistrationCompany(
            id: String,
            contactEmail: String,
            password: String,
            companyName: String,
            companyAddress: String,
            contactName: String,
            contactPhoneNumber: String,
            image: String
        ): Company {
            return Company(
                id,
                contactEmail,
                password,
                companyName,
                companyAddress,
                contactName,
                contactPhoneNumber,
                image
            )
        }
    }
}