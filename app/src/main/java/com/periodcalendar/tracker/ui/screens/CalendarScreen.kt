package com.periodcalendar.tracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    var selectedDate by remember { mutableStateOf(java.time.LocalDate.now()) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Calendar",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Month selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Previous month */ }) {
                Icon(Icons.Default.CalendarToday, contentDescription = "Previous month")
            }
            
            Text(
                text = selectedDate.month.toString() + " " + selectedDate.year,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            IconButton(onClick = { /* Next month */ }) {
                Icon(Icons.Default.CalendarToday, contentDescription = "Next month")
            }
        }
        
        // Calendar grid placeholder
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Calendar View",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Display monthly cycle view with period days, fertile window, and ovulation day marked",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Legend
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Legend",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LegendItem("Period", MaterialTheme.colorScheme.error)
            LegendItem("Fertile", MaterialTheme.colorScheme.primary)
            LegendItem("Ovulation", MaterialTheme.colorScheme.secondary)
            LegendItem("Today", MaterialTheme.colorScheme.tertiary)
        }
    }
}

@Composable
fun LegendItem(label: String, color: androidx.compose.ui.graphics.Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .padding(end = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.size(12.dp),
                color = color,
                shape = MaterialTheme.shapes.small
            ) {}
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
