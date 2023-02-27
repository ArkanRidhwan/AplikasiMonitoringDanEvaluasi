package com.example.aplikasimonitoringdanevaluasi.ui.company.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditProfileCompanyViewModel : ViewModel() {

    private val database = Firebase.database
    private val collCompany = database.getReference(Constant.COLL_COMPANY)

    fun updateCompanyById(data: Company, userId: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val company = Company.saveRegistrationCompany(
            id = userId,
            contactEmail = data.email,
            password = data.password,
            companyName = data.companyName,
            companyAddress = data.companyAddress,
            contactName = data.contactName,
            contactPhoneNumber = data.contactPhoneNumber,
            image = data.image
        )
        collCompany.child(
            data.email.replace(".", "").replace("#", "").replace("$", "")
                .replace("[", "").replace("]", "")
        ).setValue(company)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun getCompanyById(id: String): LiveData<Company?> {
        val dataCompany = MutableLiveData<Company?>()
        var company: Company? = null
        collCompany.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueCompany = i.getValue(Company::class.java)
                        if (valueCompany?.id == id) {
                            company = valueCompany
                        }
                    }
                    dataCompany.value = company
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataCompany.value = null
            }
        })
        return dataCompany
    }
}
