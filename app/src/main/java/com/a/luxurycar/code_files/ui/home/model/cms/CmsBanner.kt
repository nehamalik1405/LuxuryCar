package com.a.luxurycar.code_files.ui.home.model.cms


import com.google.gson.annotations.SerializedName

data class CmsBanner(
    @SerializedName("caption1")
    val caption1: Any?,
    @SerializedName("caption2")
    val caption2: Any?,
    @SerializedName("cms_id")
    val cmsId: Int?,
    @SerializedName("cmsbanner_content")
    val cmsbannerContent: List<CmsbannerContent?>?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)