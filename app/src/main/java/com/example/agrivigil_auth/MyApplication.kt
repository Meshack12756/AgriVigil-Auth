package com.example.agrivigil_auth

import android.app.Application

class MyApplication : Application() {
    lateinit var authRepo: AuthRepository
    override fun onCreate() {
        super.onCreate()
        authRepo = AuthRepository()
    }
}
