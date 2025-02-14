package com.example.cloneboundservice.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.cloneboundservice.model.People
import kotlin.random.Random

class MyBoundService : Service() {

    private val binder = MyBinder()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    // Các API mà Service cung cấp
    fun getText(): String {
        val texts = listOf("Hello from Bound Service", "Welcome to the app!", "Service says Hi!", "Random Text here", "Greetings!")
        return texts.random() // Random một thông điệp từ danh sách texts
    }

    fun getInt(): Int {
        return Random.nextInt(10, 100) // Random một số nguyên trong khoảng từ 10 đến 100
    }

    // Tạo mảng ngẫu nhiên mỗi lần gọi
    fun getArray(): List<People> {
        val names = listOf("John", "Jane", "Mike", "Alice", "Bob", "Eve")
        val randomPeople = mutableListOf<People>()

        // Chúng ta sẽ tạo 3 người ngẫu nhiên mỗi lần gọi
        for (i in 1..3) {
            val randomName = names.random() // Random một tên từ danh sách names
            val randomAge = Random.nextInt(20, 40) // Random một tuổi từ 20 đến 40
            randomPeople.add(People(randomName, randomAge))
        }

        return randomPeople
    }

    // Binder để kết nối với Activity
    inner class MyBinder : Binder() {
        fun getService(): MyBoundService = this@MyBoundService
    }

}