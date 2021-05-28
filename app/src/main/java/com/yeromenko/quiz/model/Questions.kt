package com.yeromenko.quiz.model

import com.google.gson.annotations.SerializedName

class Questions(
    @SerializedName("questionText") var questionText: String,
    @SerializedName("answers") var answers: MutableList<Answer>,
    @SerializedName("correctAnswerIndex") var correctAnswerIndex: Int
) {
}