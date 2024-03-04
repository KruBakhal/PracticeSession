package com.example.newappdi.NewsApp.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practicesession.R
import com.example.practicesession.databinding.ItemResultantBinding
import com.example.practicesession.superballgame.model.ResultantModel
import com.google.gson.Gson


class ResultantAdapter : RecyclerView.Adapter<ResultantAdapter.NewAdapterHolder>() {
    inner class NewAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemResultantBinding

        init {
            binding = ItemResultantBinding.bind(itemView.rootView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewAdapterHolder {
        return NewAdapterHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_resultant,
                parent,
                false
            )
        )
    }

    var onItemClickListener: ((ResultantModel, Boolean) -> Unit)? = null

    override

    fun onBindViewHolder(holder: NewAdapterHolder, position: Int) {
        val article = differ.currentList[position]!!
        holder.binding.tvGameName.setText(article.name)
        var gameListAdapter = GameListAdapter();
        gameListAdapter.differ.submitList(article.selected.map { it.copy() })
        holder.binding.rvGames.adapter = gameListAdapter

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<ResultantModel>() {
        override fun areItemsTheSame(oldItem: ResultantModel, newItem: ResultantModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ResultantModel, newItem: ResultantModel): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    fun setOnItemClickListner(listner: (ResultantModel, Boolean) -> Unit) {
        onItemClickListener = listner
    }
}


