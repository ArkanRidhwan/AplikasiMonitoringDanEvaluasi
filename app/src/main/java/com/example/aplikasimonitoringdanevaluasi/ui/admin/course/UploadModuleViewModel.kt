package com.example.aplikasimonitoringdanevaluasi.ui.admin.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Module
import com.example.aplikasimonitoringdanevaluasi.model.Video
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UploadModuleViewModel : ViewModel() {

    private val database = Firebase.database
    private val collModule = database.getReference(Constant.COLL_MODULE)

    fun saveModule(data: Module): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val admin = Module.saveModule(
            id = data.id,
            tittle = data.tittle,
            description = data.description,
            date = data.date,
            link = data.link,
        )
        collModule.child(data.id).setValue(admin)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }
}