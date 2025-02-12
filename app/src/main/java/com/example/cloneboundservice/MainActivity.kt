package com.example.cloneboundservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cloneboundservice.model.People
import com.example.cloneboundservice.service.MyBoundService
import com.example.cloneboundservice.ui.theme.CloneBoundServiceTheme

class MainActivity : ComponentActivity() {
    private var myService: MyBoundService? = null
    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyBoundService.MyBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CloneBoundServiceTheme() {
                MainScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MyBoundService::class.java)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }

    @Composable
    fun MainScreen() {
        val text = remember { mutableStateOf("") }
        val number = remember { mutableIntStateOf(0) }
        val peopleList = remember { mutableStateOf(listOf<People>()) }

        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = {
                text.value = myService?.getText() ?: "Service not bound"
            }) {
                Text("Get Text")
            }

            Button(onClick = {
                number.intValue = myService?.getInt() ?: 0
            }) {
                Text("Get Int")
            }

            Button(onClick = {
                peopleList.value = myService?.getArray() ?: emptyList()
            }) {
                Text("Get People")
            }

            Text("Text from Service: ${text.value}")
            Text("Integer from Service: ${number.intValue}")
            Text("People List: ${peopleList.value.joinToString(", ") { it.name }}")
        }
    }
}


