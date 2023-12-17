package core.components

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KpFilterBar(
    modifier: Modifier,
    filterOptions: List<String>,
    filterResults: List<MediaItem>,
    onFilter : () -> Unit = {},
    onFilterQueryChange: (String) -> Unit,
) {
    SearchBar(
        query = filterOptions.toString(),
        onQueryChange = onFilterQueryChange,
        onSearch = { onFilter() },
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
                onClick = { onFilter() },
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
                filterResults.forEach { item ->
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