package com.a.luxurycar.code_files.ui.home.model.cms


import com.google.gson.annotations.SerializedName

data class Sourcing(
    @SerializedName("cms_banner")
    val cmsBanner: CmsBannerXX?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("meta_desc")
    val metaDesc: String?,
    @SerializedName("meta_keyword")
    val metaKeyword: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("url")
    val url: String?
)