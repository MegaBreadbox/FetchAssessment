package com.mega_breadbox.fetchassement.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mega_breadbox.fetchassement.network.repos.FetchDataRepository
import com.mega_breadbox.fetchassement.screens.util.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val fetchDataRepository: FetchDataRepository
): ViewModel() {

    private val _listUiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val listUiState = _listUiState
        .onStart { fetchData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(INITIAL_DELAY),
            initialValue = ListUiState.Loading
        )

    private fun fetchData() {
        viewModelScope.launch {
            _listUiState.update { ListUiState.Loading }
            try {
                val fetchList = fetchDataRepository.getFetchData()
                    .filter { !it.name.isNullOrEmpty() }
                    .sortedBy { it.name?.substringAfter("Item ")?.toInt() }
                    .groupBy { it.listId }
                    .toSortedMap()


                _listUiState.update { ListUiState.Success(fetchList) }

            } catch (e: Exception) {
                _listUiState.update { ListUiState.Error }
            }
        }
    }

    companion object {
        const val INITIAL_DELAY = 5000L
    }
}