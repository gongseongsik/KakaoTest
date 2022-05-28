package com.example.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class Meta {
    @SerializedName("is_end")
    @Expose
    var isEnd: Boolean? = null

    @SerializedName("pageable_count")
    @Expose
    var pageableCount: Int? = null

    @SerializedName("total_count")
    @Expose
    var totalCount: Int? = null
}