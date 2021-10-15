package com.egorshustov.food2forkkmm.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeSearchResponse(

    @SerialName("count")
    val count: Int? = null,

    @SerialName("results")
    val results: List<RecipeDto>? = null
)
