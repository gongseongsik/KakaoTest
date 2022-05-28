package com.example.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.Model.Meta

class SearchRequest {
    @SerializedName("documents")
    @Expose
    var documents: List<Document>? = null

    @SerializedName("meta")
    @Expose
    var meta: Meta? = null
}