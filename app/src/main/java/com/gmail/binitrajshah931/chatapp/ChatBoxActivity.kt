package com.gmail.binitrajshah931.chatapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nkzawa.socketio.client.Socket
import com.gmail.binitrajshah931.chatapp.Adapter.ChatBoxAdapter
import com.gmail.binitrajshah931.chatapp.Constants.Constants
import com.gmail.binitrajshah931.chatapp.Constants.SocketHandler
import com.gmail.binitrajshah931.chatapp.databinding.ActivityChatBoxBinding
import org.json.JSONException
import org.json.JSONObject

class ChatBoxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBoxBinding

    private lateinit var Nickname: String
    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Nickname = intent.getStringExtra(Constants.NICKNAME).toString()

        SocketHandler.setSocket()
        socket = SocketHandler.getSocket()
        socket.connect()
        socket.emit("join", Nickname)

        //setting up recycler
        val MessageList: ArrayList<com.gmail.binitrajshah931.chatapp.model.Message> = ArrayList()
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        binding.myRecyclerView.layoutManager = mLayoutManager
        binding.myRecyclerView.itemAnimator = DefaultItemAnimator()

        // message send action
        binding.send.setOnClickListener {
            //retrieve the nickname and the message content and fire the event message detection
            if (binding.message.text.toString().isNotEmpty()) {
                socket.emit("messagedetection", Nickname, binding.message.text.toString())
                binding.message.setText(" ")
            }
        }

        //implementing socket listeners
        socket.on("userjoinedthechat") { args ->
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this@ChatBoxActivity, data, Toast.LENGTH_SHORT).show()
            }
        }

        // when user disconnects
        socket.on("userdisconnect") { args ->
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
            }
        }

        // for sending and receiving chats
        socket.on("message") { args ->
            runOnUiThread {
                val data = args[0] as JSONObject
                try {
                    //extract data from fired event
                    val nickname = data.getString("senderNickname")
                    val message = data.getString("message")

                    // make instance of message
                    val m = com.gmail.binitrajshah931.chatapp.model.Message(nickname, message)

                    //add the message to the messageList
                    MessageList.add(m)

                    // add the new updated list to the adapter
                    val chatBoxAdapter = ChatBoxAdapter(MessageList)

                    // notify the adapter to update the recycler view
                    chatBoxAdapter.notifyDataSetChanged()

                    //set the adapter for the recycler view
                    binding.myRecyclerView.adapter = chatBoxAdapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }
}

