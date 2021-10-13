package com.egorshustov.food2forkkmm.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeSearchResponse(

    @SerialName("count")
    var count: Int? = null,

    @SerialName("results")
    var results: List<RecipeDto>? = null
)
