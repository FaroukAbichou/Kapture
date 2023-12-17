package record.audio.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import core.util.FilePaths
import record.audio.presentation.event.AudioEvent
import record.audio.domain.model.Audio

@Composable
fun AudioSection() {
    val audioViewModel = rememberSaveable { AudioViewModel() }
    val state = audioViewModel.state.collectAsState()

    LaunchedEffect(Unit){
        audioViewModel.onEvent(AudioEvent.GetAudiosByPath(FilePaths.VideosPath))
    }
    LazyColumn {
        state.value.audios.forEach { audio: Audio ->
            item{
                Text(
                    text = audio.name,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier,
                )
            }
        }
    }
}
