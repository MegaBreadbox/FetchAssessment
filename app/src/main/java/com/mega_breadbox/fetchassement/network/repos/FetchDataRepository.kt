package com.mega_breadbox.fetchassement.network.repos

import com.mega_breadbox.fetchassement.network.model.FetchData

interface FetchDataRepository {

    suspend fun getFetchData(): List<FetchData>
}