package com.suhyeong.yire.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suhyeong.yire.R
import com.suhyeong.yire.api.response.SearchResult
import com.suhyeong.yire.listener.ListClickListener

class SearchResultAdapter(private val searchResults: List<SearchResult>) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    private var onClickListener: ListClickListener?= null
    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumCoverImageView: ImageView = itemView.findViewById(R.id.img_album_cover)
        val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        val musicInfoTextView: TextView = itemView.findViewById(R.id.tv_music_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_search, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val searchResult = searchResults[position]

        Glide.with(holder.itemView.context)
            .load(searchResult.artworkUrl100)
            .into(holder.albumCoverImageView)

        holder.titleTextView.text = searchResult.trackCensoredName

        val trackTimeMillis = searchResult.trackTimeMillis ?: 0L

        val minutes = (trackTimeMillis / 1000) / 60
        val seconds = (trackTimeMillis / 1000) % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)

        holder.musicInfoTextView.text = "${searchResult.artistName} · $formattedTime"

        holder.itemView.setOnClickListener {
            onClickListener?.listClickListener(searchResult)
        }
    }

    override fun getItemCount() = searchResults.size

    fun setClickListener(onClickListener: ListClickListener) {
        this.onClickListener = onClickListener
    }
}
