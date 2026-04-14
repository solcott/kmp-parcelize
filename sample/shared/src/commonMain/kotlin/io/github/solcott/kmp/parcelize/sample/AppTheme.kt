package io.github.solcott.kmp.parcelize.sample

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(content: @Composable () -> Unit) {
  val darkTheme = isSystemInDarkTheme()
  val colorScheme =
    when {
      darkTheme ->
        darkColorScheme(
          primary = Color(0xFF1E50D1),
          secondary = Color(0xFFFFD500),
          background = Color(0xFF121212),
          surface = Color(0xFF1E1E1E),
        )
      else ->
        lightColorScheme(
          primary = Color(0xFF1E50D1),
          secondary = Color(0xFFFFD500),
          background = Color(0xFFF3F6FD),
          surface = Color(0xFFFFFFFF),
        )
    }

  MaterialTheme(colorScheme = colorScheme, content = content)
}
