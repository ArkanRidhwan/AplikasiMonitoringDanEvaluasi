package com.example.aplikasimonitoringdanevaluasi.model

data class Student(
    val id: String = "",
    val name: String = "",
    val job: String = "",
    val companyName: String = "",
    val className: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = ""
) {
    companion object {
        fun saveRegistrationStudent(
            id: String,
            name: String,
            job: String,
            companyName: String,
            className: String,
            phoneNumber: String,
            email: String,
            password: String
        ): Student {
            return Student(id, name, job, companyName, className, phoneNumber, email, password)
        }
    }
}