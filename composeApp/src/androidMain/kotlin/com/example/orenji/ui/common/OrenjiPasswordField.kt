package com.example.orenji.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orenji.ui.theme.OrenJiTheme

@Composable
fun OrenJiPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Password",
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        isError = isError,
        enabled = enabled,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        visualTransformation = if (passwordVisible)
            VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible)
                        Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (passwordVisible)
                        "Hide password" else "Show password",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Preview(showBackground = true, name = "PasswordField - Empty")
@Composable
private fun OrenJiPasswordFieldPreview() {
    OrenJiTheme {
        OrenJiPasswordField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true, name = "PasswordField - Filled")
@Composable
private fun OrenJiPasswordFieldFilledPreview() {
    OrenJiTheme {
        OrenJiPasswordField(
            value = "mypassword123",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true, name = "PasswordField - Error")
@Composable
private fun OrenJiPasswordFieldErrorPreview() {
    OrenJiTheme {
        OrenJiPasswordField(
            value = "wrong",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            isError = true,
        )
    }
}