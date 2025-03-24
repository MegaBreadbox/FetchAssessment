package com.mega_breadbox.fetchassement.screens.util

import com.mega_breadbox.fetchassement.network.model.FetchData
import kotlinx.coroutines.flow.StateFlow

sealed interface ListUiState {
    data class Success(val fetchListMap: StateFlow<Map<Int, List<FetchData>>>): ListUiState
    data object Error: ListUiState
    data object Loading: ListUiState
}

