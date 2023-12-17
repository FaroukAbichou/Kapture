package record.home.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecordTitlesSection(
    modifier: Modifier = Modifier,
    selectedIndex: (Int) -> Unit,
) {
    var selectedSectionIndex by remember {
        mutableStateOf(0)
    }
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

            TextButton(
                onClick = {
                    selectedSectionIndex = 0
                    selectedIndex(selectedSectionIndex)
                },
                modifier = Modifier
            ){
                Text(
                    text = "Get Started",
                    style = if (selectedSectionIndex == 0) MaterialTheme.typography.headlineMedium
                    else MaterialTheme.typography.headlineSmall,
                    modifier = Modifier,
                    color = if (selectedSectionIndex == 0) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.9f
                    )
                )
            }

            Divider(
                modifier = Modifier
                    .height(24.dp)
                    .width(1.6.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            TextButton(
                onClick = {
                    selectedSectionIndex = 1
                    selectedIndex(selectedSectionIndex)
                },
                modifier = Modifier
            ) {
                Text(
                    text = "Videos",
                    style = if (selectedSectionIndex == 1) MaterialTheme.typography.headlineMedium
                    else MaterialTheme.typography.headlineSmall,
                    modifier = Modifier,
                    color = if (selectedSectionIndex == 1) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.9f
                    )
                )
            }
            Divider(
                modifier = Modifier
                    .height(24.dp)
                    .width(1.6.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            TextButton(
                onClick = {
                    selectedSectionIndex = 2
                    selectedIndex(selectedSectionIndex)
                },
                modifier = Modifier
            ) {
                Text(
                    text = "Images",
                    style = if (selectedSectionIndex == 2) MaterialTheme.typography.headlineMedium
                    else MaterialTheme.typography.headlineSmall,
                    modifier = Modifier,
                    color = if (selectedSectionIndex == 2) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.9f
                    )
                )
            }

            Divider(
                modifier = Modifier
                    .height(24.dp)
                    .width(1.6.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            TextButton(
                onClick = {
                    selectedSectionIndex = 3
                    selectedIndex(selectedSectionIndex)
                },
                modifier = Modifier
            ) {
                Text(
                    text = "Audios",
                    style = if (selectedSectionIndex == 3) MaterialTheme.typography.headlineMedium
                    else MaterialTheme.typography.headlineSmall,
                    modifier = Modifier,
                    color = if (selectedSectionIndex == 3) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.9f
                    )
                )
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.6.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}