package com.yeromenko.quiz.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.yeromenko.quiz.R
import com.yeromenko.quiz.Utils
import com.yeromenko.quiz.model.Root
import kotlinx.android.synthetic.main.fragment_quiz.*
import java.util.*

const val COUNT_RESULT = "com.yeromenko.quiz.COUNT_RESULT"
const val COUNT_QUESTIONS = "com.yeromenko.quiz.COUNT_QUESTIONS"
class QuizFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Reading json file from asset folder and convert to object
        val textJson = context?.let { Utils.getJsonDataFromAsset(it, "quiz.json") }
        val root = Gson().fromJson(textJson, Root::class.java)

        //Creating array of RadioButtons
        val radioButtons = listOf(
            view.findViewById<RadioButton>(R.id.rbAnswer1),
            view.findViewById(R.id.rbAnswer2),
            view.findViewById(R.id.rbAnswer3),
            view.findViewById(R.id.rbAnswer4)
        )
        //Initialization of counters
        var count = 0
        var countResult = 0
        var answerStatus = false

        txtQuestions.text = root.questions[count].questionText
        for (i in radioButtons.indices) {
            radioButtons[i].text = root.questions[count].answers[i].answerText
        }

        rgQuiz.setOnCheckedChangeListener { group, checkedId ->
            //Сhecking the correct answer
            if (checkedId != -1) {
                if (checkedId == radioButtons[root.questions[count].correctAnswerIndex].id) {
                    answerStatus = true
                    view.findViewById<RadioButton>(checkedId).background =
                        activity?.getDrawable(R.drawable.true_answer_bg)
                } else {
                    answerStatus = false
                    view.findViewById<RadioButton>(checkedId).background =
                        activity?.getDrawable(R.drawable.false_answer_bg)
                }
                //RadioButtons is not clickable
                radioButtons.forEach { it.isClickable = false }
            }
        }

        bNext.setOnClickListener { v ->
            //Increasing counters of questions and clearing of radioGroup
            val checkedId = rgQuiz.checkedRadioButtonId
            if (checkedId == -1) return@setOnClickListener
            if (answerStatus) countResult++
            if (count < root.questions.size - 1) {
                rgQuiz.clearCheck()
                count++
                radioButtons.forEach { it.isClickable = true }
                view.findViewById<RadioButton>(checkedId).background = null
                txtQuestions.text = root.questions[count].questionText
                for (i in radioButtons.indices) {
                    radioButtons[i].text = root.questions[count].answers[i].answerText
                }
            } else {
                //Go to result fragment
                val bundle = Bundle()
                bundle.putInt(COUNT_RESULT, countResult)
                bundle.putInt(COUNT_QUESTIONS, root.questions.size)
                Navigation.findNavController(v).navigate(R.id.action_quizFragment_to_resultFragment, bundle)
            }

        }
    }

}