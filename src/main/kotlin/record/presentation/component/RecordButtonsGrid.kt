package record.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import record.presentation.event.RecordEvent
import record.presentation.state.RecordState

@Composable
fun RecordButtonsGrid(
    state: RecordState,
    onEvent: (RecordEvent) -> Unit
) {
    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(2),
        state = LazyStaggeredGridState(),
        modifier = Modifier
//            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(state.recordTypes.size) { index ->
            RecordOptionButton(
                text = state.recordTypes[index].name,
                onClick = {
//                    onEvent(RecordEvent.RecordTypeSelected(index))
                },
                imageResource = state.recordTypes[index].icon,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}
