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
import com.example.masterfootball.classes.Quiz

class quizAdapter(private var onCardClicked: ((quiz: Quiz) -> Unit)): RecyclerView.Adapter<quizAdapter.ViewHolder>() {
    var quizs: MutableList<Quiz> = ArrayList()
    lateinit var context: Context

    fun quizAdapter(quizs: MutableList<Quiz>, context: Context) {
        this.quizs = quizs
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = quizs.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.card, parent, false))
    }

    override fun getItemCount(): Int {
        return quizs.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.textView5)
        val unlockedStatus = view.findViewById<ImageView>(R.id.imageView4)
        val logo = view.findViewById<ImageView>(R.id.imageView3)
        val card = view.findViewById<CardView>(R.id.card)

        fun bind(quiz:Quiz, context: Context) {
            name.text = quiz.name
            logo.setImageResource(quiz.logo)
            if (!quiz.unlocked) {
                unlockedStatus.setImageResource(R.drawable.sintitulo)
            }
            else {
                unlockedStatus.setImageResource(0)
            }
            card.setOnClickListener {
                onCardClicked(quiz)
            }
        }
    }
}