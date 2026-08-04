package com.example.mifinca.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mifinca.presentation.theme.RosePrimary
import com.example.mifinca.presentation.theme.SurfaceVariantDark
import com.example.mifinca.presentation.theme.TextPrimaryDark
import com.example.mifinca.presentation.theme.TextSecondaryDark

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextSecondaryDark) },
        modifier = modifier.fillMaxWidth(),
        isError = isError,
        supportingText = if (isError && errorMessage != null) {
            { Text(text = errorMessage, color = Color(0xFFD15774)) }
        } else null,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor       = RosePrimary,
            unfocusedBorderColor     = TextSecondaryDark.copy(alpha = 0.25f),
            focusedContainerColor    = SurfaceVariantDark,
            unfocusedContainerColor  = SurfaceVariantDark,
            focusedTextColor         = TextPrimaryDark,
            unfocusedTextColor       = TextPrimaryDark,
            cursorColor              = RosePrimary,
            focusedLabelColor        = RosePrimary,
        ),
        singleLine = true
    )
}
