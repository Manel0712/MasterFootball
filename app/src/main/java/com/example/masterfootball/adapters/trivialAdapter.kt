package com.example.masterfootball.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.masterfootball.R
import com.example.masterfootball.classes.QuestionsTrivial

class trivialAdapter(): RecyclerView.Adapter<trivialAdapter.ViewHolder>() {
    var answersTrivial: MutableList<QuestionsTrivial> = ArrayList()
    lateinit var context: Context

    fun trivialAdapter(answersTrivial: MutableList<QuestionsTrivial>, context: Context) {
        this.answersTrivial = answersTrivial
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = answersTrivial.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.review_card, parent, false))
    }

    override fun getItemCount(): Int {
        return answersTrivial.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val question = view.findViewById<TextView>(R.id.textView5)
        val answer = view.findViewById<TextView>(R.id.textView12)

        fun bind(answersTrivial:QuestionsTrivial, context: Context) {
            question.text = answersTrivial.question
            answer.text = answersTrivial.selectedOption
        }
    }
}