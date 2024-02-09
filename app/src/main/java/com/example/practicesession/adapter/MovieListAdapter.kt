package com.example.practicesession.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practicesession.databinding.ItemBNewBinding
import com.example.practicesession.datamodel.Article

class MovieListAdapter : PagingDataAdapter<Article, MoviePosterViewHolder>(MovieDiffCallBack()) {
    var onItemClickListener: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePosterViewHolder {
        return MoviePosterViewHolder(
            ItemBNewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviePosterViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onItemClickListener?.invoke(it1) }
        }
    }
    fun setOnItemClickListner(listner: (Article) -> Unit) {
        onItemClickListener = listner
    }
}

class MovieDiffCallBack : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

class MoviePosterViewHolder(
    val binding: ItemBNewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article?) {
        binding.model = article!!

    }
}