package com.mega_breadbox.fetchassement.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mega_breadbox.fetchassement.network.model.FetchData
import com.mega_breadbox.fetchassement.network.repos.FetchDataRepository
import com.mega_breadbox.fetchassement.screens.util.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    private val _removedEntries = MutableStateFlow<List<FetchData>>(emptyList())

    val fetchList = flow {
       emit(
           fetchDataRepository.getFetchData()
               .filter { !it.name.isNullOrEmpty() }
               .sortedBy { it.name}
       )
    }.combine(_removedEntries) { list, removed ->
        list.filter { !removed.contains(it)}
    }.map { list -> list.groupBy { it.listId }.toSortedMap() }

        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(INITIAL_DELAY),
        initialValue = emptyMap()
    )

    //what if name contains numbers in the front or back of the string? How would you filter it out?
    private fun fetchData() {
        viewModelScope.launch {
            _listUiState.update { ListUiState.Loading }
            try {
                _listUiState.update { ListUiState.Success(fetchList) }

            } catch (e: Exception) {
                _listUiState.update { ListUiState.Error }
            }
        }
    }

    fun deleteEntry(entry: FetchData) {
        _removedEntries.update {
            it.plus(entry)
        }
    }


    companion object {
        const val INITIAL_DELAY = 5000L
    }
}