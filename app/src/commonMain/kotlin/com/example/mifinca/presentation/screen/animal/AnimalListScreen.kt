package com.example.mifinca.presentation.screen.animal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mifinca.presentation.component.AnimalCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    // Dummy Data for UI design purposes
    val dummyAnimals = listOf(
        Triple("TAG-001", "Bovino", "active"),
        Triple("TAG-002", "Bovino", "sold"),
        Triple("TAG-003", "Porcino", "active"),
        Triple("TAG-004", "Equino", "dead")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis Animales",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(dummyAnimals.size) { index ->
                    val (tag, species, status) = dummyAnimals[index]
                    AnimalCard(
                        visualTag = tag,
                        species = species,
                        breed = "Raza Desconocida",
                        status = status,
                        onClick = { onNavigateToDetail(tag) }
                    )
                }
            }
        }
    }
}
