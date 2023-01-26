package com.example.aplikasimonitoringdanevaluasi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Admin(
    val id: String = "",
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val image: String = "",
) : Parcelable {
    companion object {
        fun saveRegistrationAdmin(
            id: String,
            email: String,
            password: String,
            name: String,
            phoneNumber: String,
            image: String
        ): Admin {
            return Admin(id, email, password, name, phoneNumber, image)
        }
    }
}