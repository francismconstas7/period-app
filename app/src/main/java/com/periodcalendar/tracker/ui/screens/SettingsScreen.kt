package com.periodcalendar.tracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var discreetMode by remember { mutableStateOf(false) }
    var cycleLength by remember { mutableStateOf("28") }
    var periodLength by remember { mutableStateOf("5") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Cycle Settings
        SettingsSection(title = "Cycle Settings") {
            OutlinedTextField(
                value = cycleLength,
                onValueChange = { cycleLength = it },
                label = { Text("Average Cycle Length (days)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedTextField(
                value = periodLength,
                onValueChange = { periodLength = it },
                label = { Text("Average Period Length (days)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Notification Settings
        SettingsSection(title = "Notifications") {
            SwitchSettingItem(
                icon = Icons.Default.Notifications,
                title = "Enable Notifications",
                subtitle = "Receive reminders for periods and pills",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            SwitchSettingItem(
                icon = Icons.Default.VisibilityOff,
                title = "Discreet Mode",
                subtitle = "Hide sensitive information in notifications",
                checked = discreetMode,
                onCheckedChange = { discreetMode = it }
            )
            
            if (discreetMode) {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = "Reminder",
                    onValueChange = { },
                    label = { Text("Discreet notification text") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Pill Reminder Settings
        SettingsSection(title = "Pill Reminder") {
            OutlinedTextField(
                value = "08:00",
                onValueChange = { },
                label = { Text("Reminder time") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null) }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedTextField(
                value = "Time to take your pill",
                onValueChange = { },
                label = { Text("Notification message") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Data & Privacy
        SettingsSection(title = "Data & Privacy") {
            SettingsItem(
                icon = Icons.Default.Backup,
                title = "Backup Data",
                subtitle = "Save your data to Google account"
            ) { /* Backup action */ }
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            SettingsItem(
                icon = Icons.Default.Restore,
                title = "Restore Data",
                subtitle = "Restore from backup"
            ) { /* Restore action */ }
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            SettingsItem(
                icon = Icons.Default.Delete,
                title = "Clear All Data",
                subtitle = "Delete all tracked data",
                destructive = true
            ) { /* Clear data action */ }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // About
        SettingsSection(title = "About") {
            SettingsItem(
                icon = Icons.Default.Info,
                title = "App Version",
                subtitle = "1.0.0"
            ) { }
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            SettingsItem(
                icon = Icons.Default.Help,
                title = "Help & Support",
                subtitle = "Get help using the app"
            ) { }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Save button
        Button(
            onClick = { /* Save settings */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Settings")
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    destructive: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (destructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (destructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        IconButton(onClick = onClick) {
            Icon(Icons.Default.ChevronRight, contentDescription = "Go")
        }
    }
}

@Composable
fun SwitchSettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
