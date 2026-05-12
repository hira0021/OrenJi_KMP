package com.example.orenji.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.orenji.ui.theme.OrenJiTheme

@Composable
fun OrenJiButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        ),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview(showBackground = true, name = "Button - Default")
@Composable
private fun OrenJiButtonPreview() {
    OrenJiTheme {
        OrenJiButton(
            text = "Some Text",
            onClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Button - Loading")
@Composable
private fun OrenJiButtonLoadingPreview() {
    OrenJiTheme {
        OrenJiButton(
            text = "Some Text",
            onClick = {},
            isLoading = true,
        )
    }
}

@Preview(showBackground = true, name = "Button - Disabled")
@Composable
private fun OrenJiButtonDisabledPreview() {
    OrenJiTheme {
        OrenJiButton(
            text = "Some Text",
            onClick = {},
            enabled = false,
        )
    }
}