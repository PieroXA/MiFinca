package com.example.mifinca.presentation.screen.animal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mifinca.presentation.component.CustomTextField
import com.example.mifinca.presentation.component.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalFormScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Animal", color = MaterialTheme.colorScheme.onPrimary) },
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
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CustomTextField(
                        value = "",
                        onValueChange = {},
                        label = "Arete Visual (Obligatorio)"
                    )
                    CustomTextField(
                        value = "",
                        onValueChange = {},
                        label = "Especie (ej. Bovino)"
                    )
                    CustomTextField(
                        value = "",
                        onValueChange = {},
                        label = "Raza"
                    )
                    CustomTextField(
                        value = "",
                        onValueChange = {},
                        label = "Fecha de Nacimiento (YYYY-MM-DD)"
                    )
                    CustomTextField(
                        value = "",
                        onValueChange = {},
                        label = "Peso (Kg)"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PrimaryButton(
                        text = "Guardar",
                        onClick = onNavigateBack
                    )
                }
            }
        }
    }
}
