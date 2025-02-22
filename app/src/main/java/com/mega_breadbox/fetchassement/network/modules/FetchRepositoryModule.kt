package com.mega_breadbox.fetchassement.network.modules

import com.mega_breadbox.fetchassement.network.repos.FetchDataRepository
import com.mega_breadbox.fetchassement.network.repos.impl.FetchDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FetchRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFetchRepository(
        fetchDataRepositoryImpl: FetchDataRepositoryImpl
    ): FetchDataRepository
}