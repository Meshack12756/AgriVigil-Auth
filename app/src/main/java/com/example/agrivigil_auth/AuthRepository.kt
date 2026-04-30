package com.example.agrivigil_auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun signUp(email: String, pass: String, phone: String, firstName: String, lastName: String, callback: (Boolean, String?) -> Unit) {
        // First check if phone number already exists
        database.getReference("users").orderByChild("phone").equalTo(phone).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    callback(false, "Phone number already registered")
                } else {
                    // Phone is unique, proceed with email/password creation
                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = auth.currentUser?.uid
                                if (userId != null) {
                                    val userMap = mapOf(
                                        "firstName" to firstName,
                                        "lastName" to lastName,
                                        "email" to email,
                                        "phone" to phone
                                    )
                                    database.getReference("users").child(userId).setValue(userMap)
                                        .addOnCompleteListener { dbTask ->
                                            if (dbTask.isSuccessful) {
                                                callback(true, null)
                                            } else {
                                                callback(false, "Failed to save user data")
                                            }
                                        }
                                } else {
                                    callback(true, null)
                                }
                            } else {
                                callback(false, task.exception?.message)
                            }
                        }
                }
            }
            .addOnFailureListener {
                callback(false, it.message)
            }
    }

    fun login(email: String, pass: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    fun resetPassword(email: String, callback: (Boolean, String?) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun logout() {
        auth.signOut()
    }
}
