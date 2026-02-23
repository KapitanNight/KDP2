package com.kapitannight.kdp2

import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.kapitannight.kdp2.repository.ChatRepository
import com.kapitannight.kdp2.ui.screens.ChatScreen
import com.kapitannight.kdp2.ui.screens.ChatsScreen
import com.kapitannight.kdp2.ui.theme.KDP2Theme
import com.kapitannight.kdp2.viewmodel.ChatViewModel
import androidx.activity.compose.setContent as setContent

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ChatRepository.init(this)
        setContent {
            KDP2Theme {
                ThemedScreen {
                    val viewModel: ChatViewModel = viewModel()
                    viewModel.loadChats()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "chats") {



                        composable("chats") {
                            val chats by viewModel.chats.collectAsStateWithLifecycle()
                            ChatsScreen(
                                chats = chats,
                                onChatClick = { chatId ->
                                    navController.navigate("chat/$chatId")
                                }
                            )
                        }



                        composable(
                            route = "chat/{chatId}",
                            arguments = listOf(navArgument("chatId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val chatId = backStackEntry.arguments?.getString("chatId") ?: "0"
                            val messages by viewModel.messages.collectAsStateWithLifecycle()



                            LaunchedEffect(chatId) {
                                viewModel.loadMessages(chatId)
                            }

                            ChatScreen(
                                messages = messages,
                                onSendMessage = { text ->
                                    viewModel.sendMessage(text, chatId)
                                },

                                onBackClick = { navController.popBackStack() }
                            )
                        }




                    }
                }
            }
        }
    }
}
