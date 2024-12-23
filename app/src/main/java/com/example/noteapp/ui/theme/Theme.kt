package com.example.noteapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Pink80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onPrimary = Purple80,
    onSecondary = PurpleGrey80,

    surface = PurpleGrey80,
    onSurface = Pink80,
    outline = Pink80

)

private val LightColorScheme = lightColorScheme(
    primary = Pink80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onPrimary = Purple80,
    onSecondary = PurpleGrey80,
    surface = PurpleGrey80,
    onSurface = Pink80,
    outline = Pink80
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/*@Composable
fun NoteAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }*/
@Composable
fun NoteAppTheme(
    darkTheme: Boolean = false, // Toggle this based on system settings or user preference
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // Default typography or customize it
        content = content
    )
}
