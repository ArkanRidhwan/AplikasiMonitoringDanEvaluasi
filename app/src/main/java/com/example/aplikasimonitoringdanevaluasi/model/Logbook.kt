package com.example.aplikasimonitoringdanevaluasi.model

data class Logbook(
    val id: String = "",
    val name: String= "",
    val content: String = "",
    val date: String = "",
) {
    companion object {
        fun saveLogbook(
            id: String,
            name: String,
            content: String,
            date: String,
        ): Student {
            return Student(id, name, content, date)
        }
    }
}