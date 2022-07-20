package com.gmail.binitrajshah931.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.github.nkzawa.socketio.client.Socket
import com.gmail.binitrajshah931.chatapp.Constants.Constants
import com.gmail.binitrajshah931.chatapp.Constants.SocketHandler
import com.gmail.binitrajshah931.chatapp.databinding.ActivityChatBoxBinding
import com.gmail.binitrajshah931.chatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        SocketHandler.setSocket()
//        val socket: Socket = SocketHandler.getSocket()
//        socket.connect()
//        socket.emit("connection")

        binding.enterChat.setOnClickListener {
            if (TextUtils.isEmpty(binding.nickname.text.toString())){
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, ChatBoxActivity::class.java)
                intent.putExtra(Constants.NICKNAME, binding.nickname.text.toString())
                startActivity(intent)
            }
        }
    }
}