package com.example.agrivigil_auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FarmerViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    private val _farmerData = MutableLiveData<Farmer?>()
    val farmerData: LiveData<Farmer?> = _farmerData

    fun fetchFarmerData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.getReference("users").child(userId).get()
                .addOnSuccessListener { snapshot ->
                    val farmer = snapshot.getValue(Farmer::class.java)
                    _farmerData.value = farmer
                }
                .addOnFailureListener {
                    _farmerData.value = null
                }
        }
    }
}
