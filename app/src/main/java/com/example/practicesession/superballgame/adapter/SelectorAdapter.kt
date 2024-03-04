package com.example.newappdi.NewsApp.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practicesession.R
import com.example.practicesession.databinding.ItemBall2Binding
import com.example.practicesession.databinding.ItemBallBinding
import com.example.practicesession.superballgame.model.BallTypeModel


class SelectorAdapter : RecyclerView.Adapter<SelectorAdapter.NewAdapterHolder>() {
    inner class NewAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemBall2Binding

        init {
            binding = ItemBall2Binding.bind(itemView.rootView)
        }
    }

    fun submitList(list: List<BallTypeModel>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewAdapterHolder {
        return NewAdapterHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_ball2,
                parent,
                false
            )
        )
    }


    var onItemClickListener: ((BallTypeModel, Int) -> Unit)? = null

    override

    fun onBindViewHolder(holder: NewAdapterHolder, position: Int) {
        val article = differ.currentList[position]!!
        holder.binding.tvText.text = article.number.toString()
        if (article.selected) {
            holder.binding.tvText.setBackgroundResource(R.drawable.shape_circle)
            holder.binding.tvText.setTextColor(Color.WHITE)
        } else {
            holder.binding.tvText.setBackgroundResource(R.drawable.shape_circlr_unselected)
            holder.binding.tvText.setTextColor(Color.BLACK)
        }
        holder.binding.tvText.setOnClickListener {
            onItemClickListener?.invoke(article, position)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<BallTypeModel>() {
        override fun areItemsTheSame(oldItem: BallTypeModel, newItem: BallTypeModel): Boolean {
            return (oldItem.number == newItem.number && oldItem.selected == newItem.selected)
        }

        override fun areContentsTheSame(oldItem: BallTypeModel, newItem: BallTypeModel): Boolean {
            return (oldItem.number == newItem.number && oldItem.selected == newItem.selected)
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    fun setOnItemClickListner(listner: (BallTypeModel, Int) -> Unit) {
        onItemClickListener = listner
    }
}


