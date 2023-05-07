package com.dev.absyid.e_news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.absyid.e_news.databinding.ItemNewsCategoryBinding
import com.dev.absyid.e_news.model.Category
import com.dev.absyid.e_news.R

class NewsCategoryAdapter(
    private val categories: List<Category>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<NewsCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
        holder.itemView.setOnClickListener {
            onCategoryClick(category)
        }
    }

    override fun getItemCount(): Int = categories.size

    class ViewHolder(private val binding: ItemNewsCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.categoryName.text = category.name
            binding.categoryImage.setImageResource(getImageResource(category.name))
        }

        private fun getImageResource(categoryName: String): Int {
            return when (categoryName) {
                "business" -> R.drawable.business
                "entertainment" -> R.drawable.entertainment
                "health" -> R.drawable.health
                "science" -> R.drawable.science
                "sports" -> R.drawable.sports
                "technology" -> R.drawable.technology
                else -> R.drawable.general
            }
        }
    }
}
