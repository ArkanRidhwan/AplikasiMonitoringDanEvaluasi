package com.example.aplikasimonitoringdanevaluasi.model

data class Company(
    val id: String = "",
    val name: String = "",
    val npwp: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = ""
) {
    companion object {
        fun saveRegistrationCompany(
            id: String,
            name: String,
            npwp: String,
            phoneNumber: String,
            email: String,
            password: String
        ): Company {
            return Company(id, name, npwp, phoneNumber, email, password)
        }
    }
}