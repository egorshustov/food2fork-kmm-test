package com.egorshustov.food2forkkmm.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(

    @SerialName("pk")
    var pk: Int? = null,

    @SerialName("title")
    var title: String? = null,

    @SerialName("publisher")
    var publisher: String? = null,

    @SerialName("featured_image")
    var featuredImage: String? = null,

    @SerialName("rating")
    var rating: Int? = null,

    @SerialName("source_url")
    var sourceUrl: String? = null,

    @SerialName("ingredients")
    var ingredients: List<String>? = null,

    @SerialName("long_date_added")
    var longDateAdded: Long? = null,

    @SerialName("long_date_updated")
    var longDateUpdated: Long? = null
)
