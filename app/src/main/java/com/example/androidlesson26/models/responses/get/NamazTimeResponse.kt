package com.example.androidlesson26.models.responses.get


import com.google.gson.annotations.SerializedName

data class NamazTimeResponse(
    @SerializedName("result")
    val namazTime: List<NamazTime>,
    @SerializedName("success")
    val success: Boolean
)