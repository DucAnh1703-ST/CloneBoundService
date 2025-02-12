package com.example.cloneboundservice.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.cloneboundservice.model.People

class MyBoundService : Service() {

    private val binder = MyBinder()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    // Các API mà Service cung cấp
    fun getText(): String {
        return "Hello from Bound Service"
    }

    fun getInt(): Int {
        return 42
    }

    fun getArray(): List<People> {
        return listOf(
            People("John", 25),
            People("Jane", 30),
            People("Mike", 35)
        )
    }

    // Binder để kết nối với Activity
    inner class MyBinder : Binder() {
        fun getService(): MyBoundService = this@MyBoundService
    }

}