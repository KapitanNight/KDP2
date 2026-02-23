package com.kapitannight.kdp2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.kapitannight.kdp2.ui.theme.KDP2Theme

@Composable
fun ThemedScreen(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()

    KDP2Theme(darkTheme = darkTheme) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Фоновое изображение
            Image(
                    painter = painterResource(id = R.drawable.bg_wallpaper),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                 )

            // Затемняющий слой
            Box(
                    modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.85f))
               )

            // Контент
            Box(
                    modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
               ) {
                content()
            }
        }
    }
}