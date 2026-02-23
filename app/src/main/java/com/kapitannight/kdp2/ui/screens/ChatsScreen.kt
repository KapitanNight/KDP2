package com.kapitannight.kdp2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapitannight.kdp2.data.model.Chat

@Composable
fun ChatsScreen(
    chats: List<Chat>,
    onChatClick: (String) -> Unit
) {
                Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                      ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                                text = "\n KDP II \n ",
                                fontSize = 24.sp,
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colorScheme.outline
                            )
                    }


                        LazyColumn(
                                contentPadding = PaddingValues(16.dp),
                                modifier = Modifier.weight(1f)
                                  ) {
                            items(chats, key = { it.id }) { chat ->
                                ChatItem(
                                        chat = chat,
                                        onClick = { onChatClick(chat.id) }
                                        )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                }
}