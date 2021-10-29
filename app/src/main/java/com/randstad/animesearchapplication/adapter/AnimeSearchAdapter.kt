package com.randstad.animesearchapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.randstad.animesearchapplication.R
import com.randstad.animesearchapplication.model.AnimeItem

class AnimeSearchAdapter(
    private val animeList: List<AnimeItem?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class AnimeSearchAdapter(view: View) : ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AnimeSearchAdapter(
            LayoutInflater.from(parent.context).inflate(R.layout.anime_search_item_details, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AnimeSearchAdapter) {
            holder.bindView(position, animeList)
        }
    }

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(
            position: Int,
            listOfAnimes: List<AnimeItem?>?
        ) {
            itemView.apply {
                listOfAnimes?.let {
                    if (position >= 0) {
                        it.get(position)?.let { animItem ->
                            Glide
                                .with(context)
                                .load(animItem.image_url)
                                .centerCrop()
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(findViewById(R.id.anime_image))
                            findViewById<AppCompatTextView>(R.id.anime_title).text = animItem.title
                            findViewById<AppCompatTextView>(R.id.anime_synopsis).text = animItem.synopsis

                        }
                    }
                }
            }
        }
    }
}