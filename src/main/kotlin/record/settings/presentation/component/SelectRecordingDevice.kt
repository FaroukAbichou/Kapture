package record.settings.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import probe.domain.model.Device

@Composable
fun SelectRecordingDevice(
    devices: List<Device>,
    selectedDevice: Device,
    onSelectDevice: (Device)-> Unit
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = devices.first().javaClass.simpleName + "s",
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        devices.forEach { device ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier,
            ) {
                RadioButton(
                    selected = (device == selectedDevice),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colors.onPrimaryContainer,
                        unselectedColor = Color.Gray,
                    ),
                    onClick = { onSelectDevice(device) }
                )
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }

}