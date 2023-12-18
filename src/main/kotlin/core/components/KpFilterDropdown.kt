package core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import record.home.presentation.component.noRippleClickable

@Composable
fun KpFilterDropdown(
    modifier: Modifier = Modifier,
    filterOptions: List<String>,
    onFilter: (String) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            text = filterOptions[selectedIndex],
            modifier = Modifier
                .height(40.dp)
                .width(200.dp)
                .background(Color.White)
                .padding(4.dp)
                .noRippleClickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(200.dp)
                .background(Color.White)
        ) {
            filterOptions.forEachIndexed { index, value ->
                DropdownMenuItem(
                    text = {
                        Text(text = value)
                    },
                    onClick = {
                        selectedIndex = index
                        expanded = false
                        onFilter(value)
                    },
                    leadingIcon = null,
                    trailingIcon = null,
                    enabled = true,
                    colors = MenuDefaults.itemColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Gray,
                    ),
                    contentPadding = PaddingValues(4.dp),
                    interactionSource = MutableInteractionSource(),
                    modifier = Modifier
                        .width(200.dp)
                        .background(Color.White)
                        .padding(4.dp),
                )
            }
        }
    }
}