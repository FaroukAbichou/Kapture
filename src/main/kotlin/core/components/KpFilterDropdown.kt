package core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import record.home.presentation.component.noRippleClickable

@Composable
fun KpFilterDropdown(
    modifier : Modifier = Modifier,
    filterOptions: List<String>,
    onFilter: (String) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    val disabledValue = "Select Filter"
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            text = filterOptions[selectedIndex],
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { expanded = true }
                .background(Color.Gray)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
        ) {
            filterOptions.forEachIndexed { index, value ->
                androidx.compose.material.DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        expanded = false
                        onFilter(value)
                    }
                ) {
                    val disabledText = if (value == disabledValue) " (Disabled)" else ""

                    Text(text = value + disabledText)
                }
            }
        }
    }
}