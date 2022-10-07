package com.example.aplikasimonitoringdanevaluasi.model

data class Admin(
    val id: String = "",
    val email: String = "",
    val password: String = "",
    val name: String = ""
){
    companion object {
        fun saveAdmin(id: String, email: String, password: String, name: String) : Admin {
            return Admin(id, email, password, name)
        }
    }
}