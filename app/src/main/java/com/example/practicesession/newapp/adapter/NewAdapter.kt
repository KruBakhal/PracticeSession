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


class NewAdapter : RecyclerView.Adapter<NewAdapter.NewAdapterHolder>() {
    inner class NewAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemBNewBinding

        init {
            binding = ItemBNewBinding.bind(itemView.rootView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewAdapterHolder {
        return NewAdapterHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_b_new,
                parent,
                false
            )
        )
    }

    var onItemClickListener: ((Article) -> Unit)? = null

    override

    fun onBindViewHolder(holder: NewAdapterHolder, position: Int) {
        val article = differ.currentList[position]!!
        holder.binding.apply {
            try {
                model = article
                root.setOnClickListener {
                    onItemClickListener?.let { it(article) }
                }
            } catch (exception: Exception) {
                Log.d("TAG", "onBindViewHolder: " + exception.message)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    fun setOnItemClickListner(listner: (Article) -> Unit) {
        onItemClickListener = listner
    }
}

@BindingAdapter("icon")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}
