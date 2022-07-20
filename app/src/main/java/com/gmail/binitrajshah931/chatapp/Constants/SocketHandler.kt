package com.gmail.binitrajshah931.chatapp.Constants

import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    private lateinit var mSocket: Socket

    @Synchronized
    fun setSocket(){
        try{
            mSocket = IO.socket("http://10.0.2.2:3000")
        }catch (e: URISyntaxException){
            e.printStackTrace()
        }
    }

    @Synchronized
    fun getSocket(): Socket{
        return mSocket
    }
}