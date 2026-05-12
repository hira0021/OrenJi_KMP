package com.example.orenji.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orenji.ui.theme.OrenJiTheme

@Composable
fun OrenJiTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        isError = isError,
        enabled = enabled,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Preview(showBackground = true, name = "TextField - Empty")
@Composable
private fun OrenJiTextFieldPreview() {
    OrenJiTheme {
        OrenJiTextField(
            value = "",
            onValueChange = {},
            label = "Email",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true, name = "TextField - Filled")
@Composable
private fun OrenJiTextFieldFilledPreview() {
    OrenJiTheme {
        OrenJiTextField(
            value = "johncena@gmail.com",
            onValueChange = {},
            label = "Email",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true, name = "TextField - Error")
@Composable
private fun OrenJiTextFieldErrorPreview() {
    OrenJiTheme {
        OrenJiTextField(
            value = "invalidemail",
            onValueChange = {},
            label = "Email",
            modifier = Modifier.fillMaxWidth(),
            isError = true,
        )
    }
}