package com.kapitannight.kdp2.ui.screens


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapitannight.kdp2.data.model.Message
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapitannight.kdp2.ThemedScreen

@Composable
fun ChatScreen(
    messages: List<Message>,
    onSendMessage: (String) -> Unit,
    onBackClick: () -> Unit
) {

    ThemedScreen {
        var text by remember { mutableStateOf("") }
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = onBackClick
                      ) {
                Text("⇦", fontSize = 24.sp, color = MaterialTheme.colorScheme.onBackground)
            }

            Text(
                    text = "\n KDP II \n ",
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Green
                )


            }

        val topBarHeight = 64.dp // или 56.dp — подбери визуально

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(top = topBarHeight)
              )  {
            val lazyListState = rememberLazyListState()
            LaunchedEffect(messages.size) {
                if (messages.isNotEmpty()) {
                    lazyListState.animateScrollToItem(messages.size - 1)
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                state = lazyListState
            ) {
                items(messages) { message ->

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp) // отступ внутри
                        ) {
                            Text(
                                text = "${message.sender}: ${message.content}",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 💬 Поле ввода текста
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("Сообщение...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(20.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,  // Чтобы поле росло при длинном тексте
                    singleLine = false
                )

                // ⬆️ Кнопка отправки (иконка вместо текста!)
                IconButton(
                    onClick = {
                        if (text.isNotBlank()) {
                            onSendMessage(text)
                            text = ""  // Очистить поле после отправки
                        }
                    },
                    enabled = text.isNotBlank()  // Неактивна, если поле пустое
                ) {
                    @Suppress("DEPRECATION")
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Отправить",
                        tint = if (text.isNotBlank())
                            MaterialTheme.colorScheme.primary  // Активная — цветная
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)  // Неактивная — серая
                    )
                }
            }
            }
    }
}