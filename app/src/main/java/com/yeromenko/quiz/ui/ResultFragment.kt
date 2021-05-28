package com.yeromenko.quiz.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.yeromenko.quiz.R
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {

    var countResult: Int = 0
    var countQuestions: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countResult = it.getInt(COUNT_RESULT, 111)
            countQuestions = it.getInt(COUNT_QUESTIONS, 111)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtResult.text =
            String.format(resources.getString(R.string.result), countResult, countQuestions)

        //Navigate to Quiz fragment
        bAgain.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_quizFragment)
        }

        //Navigate to Start fragment
        bToMain.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_startFragment)
        }

    }

}