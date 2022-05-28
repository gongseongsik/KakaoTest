package com.example.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class Document {
    @SerializedName("authors")
    @Expose
    var authors: List<String>? = null

    @SerializedName("contents")
    @Expose
    var contents: String? = null

    @SerializedName("datetime")
    @Expose
    var datetime: String? = null

    @SerializedName("isbn")
    @Expose
    var isbn: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null

    @SerializedName("publisher")
    @Expose
    var publisher: String? = null

    @SerializedName("sale_price")
    @Expose
    var salePrice: Int? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("translators")
    @Expose
    var translators: List<Any>? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
}