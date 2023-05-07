package com.dev.absyid.e_news.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.absyid.e_news.R
import com.dev.absyid.e_news.model.Sources
import com.dev.absyid.e_news.view.NewsActivity

class NewsSourcesAdapter(private var sources: List<Sources>) :
    RecyclerView.Adapter<NewsSourcesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sourceNameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        val sourceDescriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news_sources, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val source = sources[position]
        holder.sourceNameTextView.text = source.name
        holder.sourceDescriptionTextView.text = source.description
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsActivity::class.java)
            intent.putExtra("sourceId", source.id)
            holder.itemView.context.startActivity(intent)
        }
    }
    fun updateSources(newSources: List<Sources>) {
        sources = newSources
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = sources.size
}