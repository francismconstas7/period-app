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
fun LogScreen() {
    var selectedLogType by remember { mutableStateOf("Period") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Log Data",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Log type selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LogTypeButton("Period", Icons.Default.Drop, selectedLogType == "Period") {
                selectedLogType = "Period"
            }
            LogTypeButton("Symptoms", Icons.Default.EmojiEmotions, selectedLogType == "Symptoms") {
                selectedLogType = "Symptoms"
            }
            LogTypeButton("Mood", Icons.Default.SentimentSatisfied, selectedLogType == "Mood") {
                selectedLogType = "Mood"
            }
            LogTypeButton("Notes", Icons.Default.Edit, selectedLogType == "Notes") {
                selectedLogType = "Notes"
            }
        }
        
        when (selectedLogType) {
            "Period" -> PeriodLogForm()
            "Symptoms" -> SymptomsLogForm()
            "Mood" -> MoodLogForm()
            "Notes" -> NotesLogForm()
        }
    }
}

@Composable
fun LogTypeButton(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        leadingIcon = if (selected) {
            { Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp)) }
        } else null
    )
}

@Composable
fun PeriodLogForm() {
    var flowIntensity by remember { mutableStateOf("Medium") }
    var notes by remember { mutableStateOf("") }
    
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
                text = "Log Period",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(text = "Flow Intensity", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Spotting", "Light", "Medium", "Heavy").forEach { intensity ->
                    FilterChip(
                        selected = flowIntensity == intensity,
                        onClick = { flowIntensity = intensity },
                        label = { Text(intensity) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (optional)") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* Save period log */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Period Log")
            }
        }
    }
}

@Composable
fun SymptomsLogForm() {
    val symptoms = listOf("Cramps", "Headaches", "Bloating", "Fatigue", "Acne", "Backache")
    var selectedSymptoms by remember { mutableStateOf(setOf<String>()) }
    
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
                text = "Log Symptoms",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            symptoms.forEach { symptom ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(symptom)
                    Checkbox(
                        checked = symptom in selectedSymptoms,
                        onCheckedChange = { checked ->
                            selectedSymptoms = if (checked) {
                                selectedSymptoms + symptom
                            } else {
                                selectedSymptoms - symptom
                            }
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* Save symptoms */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Symptoms")
            }
        }
    }
}

@Composable
fun MoodLogForm() {
    var selectedMood by remember { mutableStateOf("") }
    
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
                text = "Log Mood",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Happy" to Icons.Default.SentimentSatisfied,
                       "Calm" to Icons.Default.SentimentVerySatisfied,
                       "Sad" to Icons.Default.SentimentDissatisfied,
                       "Anxious" to Icons.Default.SentimentVeryDissatisfied).forEach { (mood, icon) ->
                    FilterChip(
                        selected = selectedMood == mood,
                        onClick = { selectedMood = mood },
                        label = { Text(mood) },
                        leadingIcon = { Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* Save mood */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Mood")
            }
        }
    }
}

@Composable
fun NotesLogForm() {
    var noteText by remember { mutableStateOf("") }
    
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
                text = "Add Note",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("Your note") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 10
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* Save note */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Note")
            }
        }
    }
}
