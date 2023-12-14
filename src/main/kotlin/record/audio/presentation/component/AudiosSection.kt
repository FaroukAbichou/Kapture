package record.audio.presentation.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import core.util.FilePaths
import record.audio.domain.model.Audio
import record.audio.presentation.event.AudioEvent
import record.audio.presentation.state.AudioState

@Composable
fun AudiosSection(
    state: AudioState,
    onEvent: (AudioEvent) -> Unit
) {
     LaunchedEffect(Unit){
         onEvent(AudioEvent.GetAudiosByPath(FilePaths.AudiosPath))
     }
     LazyColumn {
         state.audios.forEach { audio: Audio ->
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