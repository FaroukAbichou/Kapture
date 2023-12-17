package record.home.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.MediaItem
import record.video.domain.model.Video

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KpSearchBar(
    modifier : Modifier = Modifier,
    searchQuery: String,
    onSearch : () -> Unit = {},
    searchResults: List<MediaItem>,
    onSearchQueryChange: (String) -> Unit,
) {
    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = { onSearch() },
        placeholder = {
            Text(
                text = "Search...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { onSearch() },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            }
        },
        content = {
            LazyColumn(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start
            ) {
                searchResults.forEach { item ->
                    item{
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier,
                        )
                    }
                }
            }

        },
        active = true,
        onActiveChange = {

        },
        tonalElevation = 0.dp,
        modifier = modifier
    )
}