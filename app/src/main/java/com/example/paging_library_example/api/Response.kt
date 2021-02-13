package com.example.paging_library_example.api

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("total_count")
    val totalCount : Int = 0,
    @SerializedName("incomplete_results")
    val incompleteResults : Boolean = false,
    @SerializedName("items")
    val items : List<Contents>,
    val nextPage : Int? = null
)