package com.a.luxurycar.code_files.ui.home.model.faqresponse


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("answer")
    val answer: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("question")
    val question: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)