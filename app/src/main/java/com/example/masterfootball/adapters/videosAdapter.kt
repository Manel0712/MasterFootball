package com.example.masterfootball.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.masterfootball.R
import com.example.masterfootball.classes.Video

class videosAdapter(private var onCardClicked: ((video: Video) -> Unit)): RecyclerView.Adapter<videosAdapter.ViewHolder>() {
    var videos: MutableList<Video> = ArrayList()
    lateinit var context: Context

    fun videosAdapter(videos: MutableList<Video>, context: Context) {
        this.videos = videos
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = videos.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.card, parent, false))
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.textView5)
        val unlockedStatus = view.findViewById<ImageView>(R.id.imageView4)
        val logo = view.findViewById<ImageView>(R.id.imageView3)
        val card = view.findViewById<CardView>(R.id.cardReview)

        fun bind(video:Video, context: Context) {
            name.text = video.name
            logo.setImageResource(video.logo)
            if (!video.unlocked) {
                unlockedStatus.setImageResource(R.drawable.sintitulo)
            }
            else {
                unlockedStatus.setImageResource(0)
            }
            card.setOnClickListener {
                onCardClicked(video)
            }
        }
    }
}