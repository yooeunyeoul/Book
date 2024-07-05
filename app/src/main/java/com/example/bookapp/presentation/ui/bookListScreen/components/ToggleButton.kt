package com.example.bookapp.presentation.ui.bookListScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookapp.presentation.util.clickableSingle

@Composable
fun CustomToggleButton(selected: ViewType, onSelected: (ViewType) -> Unit) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(50))
            .background(Color.White, RoundedCornerShape(50))
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ToggleItem(ViewType.LIST, selected, onSelected)
        ToggleItem(ViewType.GRID, selected, onSelected)
    }
}

@Composable
fun ToggleItem(
    text: ViewType,
    selected: ViewType,
    onSelected: (ViewType) -> Unit
) {
    val isSelected = text == selected
    Box(
        modifier = Modifier
            .height(56.dp)
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) Color(0xFFE3F2FD) else Color.Transparent)
            .clickableSingle { onSelected(text) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.name,
            color = if (isSelected) Color(0xFF1976D2) else Color.Gray,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

enum class ViewType {
    LIST, GRID
}
