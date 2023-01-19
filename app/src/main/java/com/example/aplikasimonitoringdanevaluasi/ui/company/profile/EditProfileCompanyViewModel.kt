package com.example.aplikasimonitoringdanevaluasi.ui.company.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditProfileCompanyViewModel : ViewModel() {

    private val database = Firebase.database
    private val collCompany = database.getReference(Constant.COLL_COMPANY)

    fun updateCompany(data: Company, userId: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val company = Company.saveRegistrationCompany(
            id = userId,
            contactEmail = data.contactEmail,
            password = data.password,
            companyName = data.companyName,
            companyAddress = data.companyAddress,
            contactName = data.contactName,
            contactPhoneNumber = data.contactPhoneNumber,
            image = data.image
        )
        collCompany.child(userId).setValue(company)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }
}
