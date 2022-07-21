package com.gmail.binitrajshah931.chatapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.binitrajshah931.chatapp.R
import com.gmail.binitrajshah931.chatapp.model.Message


class ChatBoxAdapter(MessagesList: List<Message>) :
    RecyclerView.Adapter<ChatBoxAdapter.MyViewHolder>() {

    companion object {
        private const val ITEM_TYPE_SEND = 0
        private const val ITEM_TYPE_RECEIVER = 1
    }

    private val MessageList: List<Message>

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nickname: TextView
        var message: TextView

        init {
            nickname = view.findViewById<View>(R.id.nickname) as TextView
            message = view.findViewById<View>(R.id.message) as TextView
        }
    }

    override fun getItemCount(): Int {
        return MessageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //binding the data from our ArrayList of object to the item.xml using the viewholder
        val m: Message = MessageList[position]
        holder.nickname.text = m.nickname
        holder.message.text = m.message
    }

    // in this adapter constructor we add the list of messages as a parameter so that
    // we will pass  it when making an instance of the adapter object in our activity
    init {
        MessageList = MessagesList
    }
}