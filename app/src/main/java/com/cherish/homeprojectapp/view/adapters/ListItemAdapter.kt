package com.cherish.homeprojectapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cherish.homeprojectapp.databinding.ListItemLayoutBinding



class ListItemAdapter(private val callback: (ListItemData) -> Unit): ListAdapter<ListItemData, ListItemAdapter.ListItemViewHolder>(PojoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }


    inner class ListItemViewHolder(private val binding: ListItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item : ListItemData){
            binding.name.text = item.login
            binding.description.text = item.description
            binding.url.text = item.url
            com.cherish.homeprojectapp.utils.GlideApp.with(binding.wrapper)
                .load(item.avatar_url)
                .into(binding.image)
            binding.item.setOnClickListener{
               val position = getItem(adapterPosition)
                callback(position)
            }
        }
    }

    object PojoDiffCallback : DiffUtil.ItemCallback<ListItemData>() {
        override fun areItemsTheSame(
            oldItem: ListItemData,
            newItem: ListItemData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ListItemData,
            newItem: ListItemData
        ): Boolean {
            return oldItem == newItem
        }
    }




}