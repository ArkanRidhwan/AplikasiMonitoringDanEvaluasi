package com.example.aplikasimonitoringdanevaluasi.ui.admin.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditProfileAdminViewModel : ViewModel() {

    private val database = Firebase.database
    private val collAdmin = database.getReference(Constant.COLL_ADMIN)

    fun updateAdmin(data: Admin, userId: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val admin = Admin.saveRegistrationAdmin(
            id = userId,
            email = data.email,
            password = data.password,
            name = data.name,
            phoneNumber = data.phoneNumber,
            image = data.image
        )
        collAdmin.child(userId).setValue(admin)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }
}

