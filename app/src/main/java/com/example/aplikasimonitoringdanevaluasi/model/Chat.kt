package com.example.aplikasimonitoringdanevaluasi.model

data class Chat(
    val id: String = "",
    val receiverId: String = "",
    val receiverName: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val message: String = "",
    val date: Long = 0
)