package com.apex.codeassesment.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ItemUserBinding

class UsersAdapter() : RecyclerView.Adapter<UsersAdapter.VH>() {

    var mList = listOf<User>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: ((item: User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.run {
        holder.bind(mList[position])
        holder.itemClick = itemClickListener
    }

    override fun getItemCount() = mList.size

    inner class VH(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var itemClick: ((item: User) -> Unit)? = null

        fun bind(item: User) {

            binding.tvUser.text = item.toString()

            itemView.setOnClickListener {
                itemClick?.invoke(item)
            }

        }

    }

}
