package com.kapitannight.kdp2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kapitannight.kdp2.data.model.Chat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ChatItem(
        chat: Chat,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
            )
{
    Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                .padding(16.dp)
       ) {
        Row(
                verticalAlignment = Alignment.CenterVertically
           ) {
            // Аватарка (временно — буква)
            Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
               ) {
                Text(
                        text = chat.name.first().uppercaseChar().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                    modifier = Modifier.weight(1f)
                  ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                            text = chat.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    if (chat.isOnline)
                    {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.Green, CircleShape)
                           )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                val isEmpty = chat.lastMessage.isEmpty()

                Text(
                    text = if (isEmpty) "Нет сообщений" else chat.lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isEmpty)
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            // Время (опционально)
            Text(
                    text = formatTimestamp(chat.timestamp), // реализуй эту функцию
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
        }
    }
}
private fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(timestamp)
}