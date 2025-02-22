package com.mega_breadbox.fetchassement.network.model

import kotlinx.serialization.Serializable

@Serializable
data class FetchData(
    val id: Int,
    val listId: Int,
    val name: String?
)
