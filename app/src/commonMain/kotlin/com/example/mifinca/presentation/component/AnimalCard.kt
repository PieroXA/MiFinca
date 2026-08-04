package com.example.mifinca.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mifinca.presentation.theme.GoldAccent
import com.example.mifinca.presentation.theme.OrangeSecondary
import com.example.mifinca.presentation.theme.RosePrimary
import com.example.mifinca.presentation.theme.SurfaceDark
import com.example.mifinca.presentation.theme.TextPrimaryDark
import com.example.mifinca.presentation.theme.TextSecondaryDark

@Composable
fun AnimalCard(
    visualTag: String,
    species: String,
    status: String,
    breed: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Color del avatar según especie
    val avatarGradient = when (species.lowercase().take(3)) {
        "bov" -> listOf(RosePrimary, OrangeSecondary)
        "por" -> listOf(OrangeSecondary, GoldAccent)
        "equ" -> listOf(Color(0xFF8B6BBF), RosePrimary)
        else  -> listOf(GoldAccent, OrangeSecondary)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SurfaceDark)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(colors = avatarGradient)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = species.take(1).uppercase(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Arete: $visualTag",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )
                Text(
                    text = "$species · $breed",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryDark
                )
            }

            StatusBadge(status = status)
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val (bgColor, textColor, label) = when (status.lowercase()) {
        "active"   -> Triple(Color(0xFF1D3A2A), Color(0xFF6BBF5E), "Activo")
        "sold"     -> Triple(Color(0xFF1A2A3A), Color(0xFF5AAFD4), "Vendido")
        "dead"     -> Triple(Color(0xFF3A1A1A), Color(0xFFD15774), "Muerto")
        else       -> Triple(Color(0xFF2A2A2A), Color(0xFF9CA3AF), status.uppercase())
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            fontSize = 11.sp
        )
    }
}
