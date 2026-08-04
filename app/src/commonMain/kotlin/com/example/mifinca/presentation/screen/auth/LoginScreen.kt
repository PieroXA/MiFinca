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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mifinca.presentation.component.CustomTextField
import com.example.mifinca.presentation.component.PrimaryButton
import com.example.mifinca.presentation.theme.BackgroundDark
import com.example.mifinca.presentation.theme.GoldAccent
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
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated && uiState.authResult != null) {
            onLoginSuccess(uiState.authResult!!.tenantId)
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
                .height(300.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(GradientStart, GradientMid, GradientEnd)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Círculo decorativo grande detrás
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(Color.White.copy(alpha = 0.06f))
                    .align(Alignment.TopEnd)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 16.dp)
            ) {
                // Icono en caja blanca translúcida
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🐄", fontSize = 34.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "MiFinca",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = (-0.5).sp
                )

                Text(
                    text = "Gestión Ganadera Inteligente",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.82f),
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }

        // ── Curva de unión ────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .background(GradientMid)
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Título del formulario
            Column {
                Text(
                    text = "Bienvenido de vuelta",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )
                Text(
                    text = "Ingresa tus credenciales para continuar",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryDark,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // Campos
            CustomTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = "Correo Electrónico",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            CustomTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = "Contraseña",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
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

            PrimaryButton(
                text = "Iniciar Sesión",
                isLoading = uiState.isLoading,
                onClick = { viewModel.login() }
            )

            // Divisor
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = TextSecondaryDark.copy(alpha = 0.2f)
                )
                Text(
                    text = "o",
                    color = TextSecondaryDark,
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = TextSecondaryDark.copy(alpha = 0.2f)
                )
            }

            // Registro
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceVariantDark)
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                TextButton(onClick = onNavigateToRegister) {
                    Text(
                        text = "¿No tienes cuenta? ",
                        color = TextSecondaryDark,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Crear cuenta",
                        color = RosePrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
