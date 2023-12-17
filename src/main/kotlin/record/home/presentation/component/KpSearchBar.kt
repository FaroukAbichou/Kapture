package record.home.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import core.MediaItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KpSearchBar(
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
        trailingIcon = {},
        content = {},
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    )
}