package com.mega_breadbox.fetchassement.network.repos.impl

import com.mega_breadbox.fetchassement.network.FetchApiService
import com.mega_breadbox.fetchassement.network.model.FetchData
import com.mega_breadbox.fetchassement.network.repos.FetchDataRepository
import javax.inject.Inject

class FetchDataRepositoryImpl @Inject constructor(
    private val fetchApiService: FetchApiService
): FetchDataRepository {

    override suspend fun getFetchData(): List<FetchData> {
        return fetchApiService.getData()
    }

}