package com.egorshustov.food2forkkmm.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(

    @SerialName("pk")
    val pk: Int? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("publisher")
    val publisher: String? = null,

    @SerialName("featured_image")
    val featuredImage: String? = null,

    @SerialName("rating")
    val rating: Int? = null,

    @SerialName("source_url")
    val sourceUrl: String? = null,

    @SerialName("ingredients")
    val ingredients: List<String>? = null,

    @SerialName("long_date_added")
    val longDateAdded: Long? = null,

    @SerialName("long_date_updated")
    val longDateUpdated: Long? = null
)
