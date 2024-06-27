package com.example.androidlesson26.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson26.databinding.ItemNamaztimeBinding
import com.example.androidlesson26.models.responses.get.NamazTime

class NamazTimeAdapder : RecyclerView.Adapter<NamazTimeAdapder.UserViewHolder>() {

    private val list = arrayListOf<NamazTime>()


    inner class UserViewHolder(val itemBinding: ItemNamaztimeBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemNamaztimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = list[position]
        holder.itemBinding.item = item

    }

    fun updateList(newList: List<NamazTime>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}