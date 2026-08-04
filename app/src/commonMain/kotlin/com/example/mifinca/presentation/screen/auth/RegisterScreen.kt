package com.example.mifinca.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mifinca.presentation.component.CustomTextField
import com.example.mifinca.presentation.component.PrimaryButton
import com.example.mifinca.presentation.theme.BackgroundDark
import com.example.mifinca.presentation.theme.GradientEnd
import com.example.mifinca.presentation.theme.GradientMid
import com.example.mifinca.presentation.theme.GradientStart
import com.example.mifinca.presentation.theme.OrangeSecondary
import com.example.mifinca.presentation.theme.RosePrimary
import com.example.mifinca.presentation.theme.SurfaceDark
import com.example.mifinca.presentation.theme.SurfaceVariantDark
import com.example.mifinca.presentation.theme.TextPrimaryDark
import com.example.mifinca.presentation.theme.TextSecondaryDark
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: (String) -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered && uiState.authResult != null) {
            onRegisterSuccess(uiState.authResult!!.tenantId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
    ) {
        // ── ZONA SUPERIOR: branding con degradado ─────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(GradientMid, GradientEnd, GradientStart)
                    )
                )
        ) {
            // Botón back arriba izquierda
            IconButton(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier.align(Alignment.BottomStart).padding(28.dp, bottom = 24.dp)
            ) {
                Text(
                    text = "🌾",
                    fontSize = 36.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Crear Cuenta",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Registra tu finca en minutos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // Curva de unión
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .background(GradientStart)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .background(BackgroundDark)
            )
        }

        // ── ZONA INFERIOR: formulario ──────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundDark)
                .padding(horizontal = 28.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Sección personal
            SectionHeader(number = "01", title = "Tu información")

            CustomTextField(
                value = uiState.fullName,
                onValueChange = { viewModel.onFullNameChange(it) },
                label = "Nombre completo",
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            CustomTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = "Correo Electrónico",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            CustomTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = "Contraseña  (mín. 8 caracteres)",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Sección finca
            SectionHeader(number = "02", title = "Tu finca")

            CustomTextField(
                value = uiState.farmName,
                onValueChange = { viewModel.onFarmNameChange(it) },
                label = "Nombre de la finca",
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            // Error
            if (uiState.error != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(RosePrimary.copy(alpha = 0.12f))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "⚠️  ${uiState.error!!}",
                        color = RosePrimary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            PrimaryButton(
                text = "Crear mi cuenta",
                isLoading = uiState.isLoading,
                onClick = { viewModel.register() }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceVariantDark)
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                TextButton(onClick = onNavigateToLogin) {
                    Text(
                        text = "¿Ya tienes cuenta? ",
                        color = TextSecondaryDark,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Iniciar sesión",
                        color = RosePrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionHeader(number: String, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Brush.linearGradient(colors = listOf(RosePrimary, OrangeSecondary))
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimaryDark
        )
    }
}
