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
import com.example.masterfootball.classes.QuestionQuiz
import com.example.masterfootball.classes.Quiz

class quizAnswersAdapter(): RecyclerView.Adapter<quizAnswersAdapter.ViewHolder>() {
    var answersQuizs: MutableList<QuestionQuiz> = ArrayList()
    lateinit var context: Context

    fun quizAnswersAdapter(answersQuiz: MutableList<QuestionQuiz>, context: Context) {
        this.answersQuizs = answersQuiz
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = answersQuizs.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.review_card, parent, false))
    }

    override fun getItemCount(): Int {
        return answersQuizs.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val question = view.findViewById<TextView>(R.id.textView5)
        val answer = view.findViewById<TextView>(R.id.textView12)

        fun bind(answersQuiz:QuestionQuiz, context: Context) {
            question.text = answersQuiz.question
            answer.text = answersQuiz.selectedOption
        }
    }
}