package com.example.aplikasimonitoringdanevaluasi.model

data class Student(
    val id: String = "",
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val companyName: String = "",
    val job: String = "",
    val className: String = "",
    val phoneNumber: String = "",
    val studentMajor: String = "",
    val image: String = "",
) {
    companion object {
        fun saveRegistrationStudent(
            id: String,
            email: String,
            password: String,
            name: String,
            companyName: String,
            job: String,
            className: String,
            phoneNumber: String,
            studentMajor: String,
        ): Student {
            return Student(id, email, password, name, companyName, job, className, phoneNumber, studentMajor)
        }
    }
}