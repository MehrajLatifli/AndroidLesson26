package com.example.androidlesson26.models.responses.get


import com.google.gson.annotations.SerializedName

data class NamazTime(
    @SerializedName("saat")
    val saat: String,
    @SerializedName("vakit")
    val vakit: String
)