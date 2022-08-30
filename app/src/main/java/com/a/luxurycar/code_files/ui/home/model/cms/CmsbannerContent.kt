package com.a.luxurycar.code_files.ui.home.model.cms


import com.google.gson.annotations.SerializedName

data class CmsbannerContent(
    @SerializedName("caption_heading")
    val captionHeading: Any?,
    @SerializedName("cms_banner_id")
    val cmsBannerId: Int?,
    @SerializedName("cms_id")
    val cmsId: Int?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("heading")
    val heading: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)