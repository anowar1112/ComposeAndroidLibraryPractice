package com.schedule.androidcomposelibrary.notification.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.schedule.androidcomposelibrary.notification.SupabaseManager
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
//
//
//class MessageRepository(private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)) {
//
//    var messages by mutableStateOf(listOf<Message>())
//        private set
//
//    private val supabase = SupabaseManager.client
//
//    // ----------------------------
//    // Send a message
//    // ----------------------------
//    fun sendMessage(sender: String, text: String) {
//        scope.launch {
//            try {
//                supabase.postgrest
//                    .from("messages")
//                    .insert<Map<String, Any?>>(
//                        mapOf(
//                            "sender" to sender,
//                            "message" to text
//                        )
//                    )
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    // ----------------------------
//    // Observe messages realtime
//    // ----------------------------
//    fun observeMessages() {
//        val channel = supabase.realtime.channel("public:messages") // ✅ correct
//        scope.launch {
//            channel.postgresChangeFlow<Map<String, Any?>>(
//                schema = "public",
//                table = "messages"
//            ).collect { change ->
//                try {
//                    val newMsg = Message(
//                        id = (change.record["id"] as Number).toLong(),
//                        sender = change.record["sender"] as String,
//                        message = change.record["message"] as String,
//                        created_at = change.record["created_at"] as String
//                    )
//                    messages = messages + newMsg
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        }
//        channel.subscribe()
//    }
//
//    // ----------------------------
//    // Fetch existing messages (optional)
//    // ----------------------------
//    fun fetchMessages() {
//        scope.launch {
//            try {
//                val result = supabase.postgrest.from("messages").select().execute()
//                val data = result.data as? List<Map<String, Any?>> ?: emptyList()
//                messages = data.map {
//                    Message(
//                        id = (it["id"] as Number).toLong(),
//                        sender = it["sender"] as String,
//                        message = it["message"] as String,
//                        created_at = it["created_at"] as String
//                    )
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//}
