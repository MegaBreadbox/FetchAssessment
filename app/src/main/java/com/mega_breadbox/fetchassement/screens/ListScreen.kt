package com.mega_breadbox.fetchassement.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mega_breadbox.fetchassement.R
import com.mega_breadbox.fetchassement.network.model.FetchData
import com.mega_breadbox.fetchassement.screens.util.ListUiState

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val listUiState by viewModel.listUiState.collectAsStateWithLifecycle()
    val listMap by viewModel.fetchList.collectAsStateWithLifecycle()

    when(listUiState) {
        is ListUiState.Success -> {
            FetchList(
            fetchList = listMap,
            onCardClick =  { viewModel.deleteEntry(it)},
            )
        }
        is ListUiState.Error -> { ErrorScreen() }
        is ListUiState.Loading -> { LoadingScreen() }
    }

}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.generic_error),
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .size(dimensionResource(id = R.dimen.loading_circle_size))
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FetchList(
    fetchList: Map<Int, List<FetchData>>,
    onCardClick: (FetchData) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding)),
    ) {
        item {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.header_padding)))
        }

        fetchList.forEach { (listId, items) ->
            stickyHeader {
                Text(
                    text = listId.toString(),
                    modifier = modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.medium_padding))
                )
            }

            items(items) { itemEntry ->
                FetchItem(
                    fetchItem = itemEntry,
                    onClick = onCardClick
                )
            }
        }

    }
}

@Composable
fun FetchItem(
    fetchItem: FetchData,
    onClick: (FetchData) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(fetchItem) }
            .padding(horizontal = dimensionResource(id = R.dimen.small_padding))

    ) {
        Row(
            modifier = modifier
                .padding(horizontal = dimensionResource(id = R.dimen.small_padding))
        ) {
            Text(text = fetchItem.listId.toString())
            fetchItem.name?.let { Text(text = it) }
        }
    }
}