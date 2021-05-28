package com.yeromenko.quiz.model

import com.google.gson.annotations.SerializedName

class Root(@SerializedName("questions") var questions: MutableList<Questions>) {
}