package com.alpha2048.coroutinesflowtest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alpha2048.coroutinesflowtest.databinding.ItemRepoBinding

class MainAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val repoItem = viewModel.repoItems[position]
        holder.binding.repoItem = repoItem
        holder.binding.repoTextStargazersCount.text = repoItem.stargazers_count.toString()
    }

    override fun getItemCount(): Int {
        return viewModel.repoItems.size
    }

}

class ItemViewHolder(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root)