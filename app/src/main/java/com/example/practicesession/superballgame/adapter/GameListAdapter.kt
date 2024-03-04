package com.example.newappdi.NewsApp.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicesession.newapp.datamodel.Article
import com.example.practicesession.R
import com.example.practicesession.databinding.ItemBNewBinding
import com.example.practicesession.databinding.ItemBall2Binding
import com.example.practicesession.databinding.ItemGamesListBinding
import com.example.practicesession.superballgame.model.BallTypeModel
import com.example.practicesession.superballgame.model.SuperBallModel


class GameListAdapter(val isResult: Boolean = false) :
    RecyclerView.Adapter<GameListAdapter.NewAdapterHolder>() {
    inner class NewAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemGamesListBinding

        init {
            binding = ItemGamesListBinding.bind(itemView.rootView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewAdapterHolder {
        return NewAdapterHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_games_list,
                parent,
                false
            )
        )
    }

    var onItemClickListener: ((SuperBallModel, Boolean) -> Unit)? = null

    override

    fun onBindViewHolder(holder: NewAdapterHolder, position: Int) {
        val article = differ.currentList[position]!!

        var selectedAdapter = SelectorAdapter()
        selectedAdapter.differ.submitList(article.selectedList!!.map { it.copy() })
        holder.binding.rvSelected.adapter = selectedAdapter
        if (!isResult) {
            holder.binding.layDeletEdit.visibility = View.VISIBLE
        }

        holder.binding.imgEdit.setOnClickListener {
            onItemClickListener?.invoke(article, true)
        }
        holder.binding.imgDelete.setOnClickListener {
            onItemClickListener?.invoke(article, false)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<SuperBallModel>() {
        override fun areItemsTheSame(oldItem: SuperBallModel, newItem: SuperBallModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SuperBallModel, newItem: SuperBallModel): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    fun setOnItemClickListner(listner: (SuperBallModel, Boolean) -> Unit) {
        onItemClickListener = listner
    }
}


