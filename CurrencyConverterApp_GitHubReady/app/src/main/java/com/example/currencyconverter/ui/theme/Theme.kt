 package com.example.currencyconverter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable

private val DarkColors = darkColors(
    primary = Color(0xFF90CAF9),
    secondary = Color(0xFF80CBC4)
)

private val LightColors = lightColors(
    primary = Color(0xFF1976D2),
    secondary = Color(0xFF009688)
)

@Composable
fun CurrencyConverterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
