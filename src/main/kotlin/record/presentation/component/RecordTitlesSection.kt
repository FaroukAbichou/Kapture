package record.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecordTitlesSection(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Get Started",
                style = if (selectedIndex == 0) MaterialTheme.typography.headlineMedium
                else MaterialTheme.typography.headlineSmall,
                modifier = Modifier,
                color = if (selectedIndex == 0) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.8f
                )
            )

            Divider(
                modifier = Modifier
                    .height(24.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = "Videos",
                style = if (selectedIndex == 1) MaterialTheme.typography.headlineMedium
                else MaterialTheme.typography.headlineSmall,
                modifier = Modifier,
                color = if (selectedIndex == 1) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.8f
                )
            )

            Divider(
                modifier = Modifier
                    .height(24.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = "Images",
                style = if (selectedIndex == 2) MaterialTheme.typography.headlineMedium
                else MaterialTheme.typography.headlineSmall,
                modifier = Modifier,
                color = if (selectedIndex == 2) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.8f
                )
            )

            Divider(
                modifier = Modifier
                    .height(24.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = "Audios",
                style = if (selectedIndex == 3) MaterialTheme.typography.headlineMedium
                else MaterialTheme.typography.headlineSmall,
                modifier = Modifier,
                color = if (selectedIndex == 3) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.8f
                )
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}