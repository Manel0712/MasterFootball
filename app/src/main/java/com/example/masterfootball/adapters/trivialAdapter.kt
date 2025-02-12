package com.example.masterfootball.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.masterfootball.R
import com.example.masterfootball.classes.Trivial

class trivialAdapter (private var onCardClicked: ((trivial: Trivial ) -> Unit)) : RecyclerView.Adapter<trivialAdapter.ViewHolder>()  {

    var trivial: MutableList<Trivial> = ArrayList()
    lateinit var context: Context

    fun trivialAdapter(trivial: MutableList<Trivial>, context: Context) {
        this.trivial = trivial
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = trivial.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.card, parent, false))
    }

    override fun getItemCount(): Int {
        return trivial.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val answer = view.findViewById<TextView>(R.id.reviewPregunta)
        val card = view.findViewById<CardView>(R.id.cardReview)

        fun bind(trivial: Trivial, context: Context) {
            answer.text = trivial.answer
            card.setOnClickListener {
                onCardClicked(trivial)
            }
        }
    }

}