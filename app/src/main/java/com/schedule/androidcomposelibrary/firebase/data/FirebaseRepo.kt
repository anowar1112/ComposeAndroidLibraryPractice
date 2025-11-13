package com.schedule.androidcomposelibrary.firebase.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseRepo {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Sign up: create user + user document
    suspend fun signUp(name: String, email: String, password: String, parentEmail: String?): Result<Unit> {
        return try {
            val res = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = res.user!!.uid
            val user = mapOf(
                "uid" to uid,
                "name" to name,
                "email" to email,
                "parentEmail" to parentEmail,
                "createdAt" to System.currentTimeMillis()
            )
            db.collection("users").document(uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Login
    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun currentUid(): String? = auth.currentUser?.uid

    // Get user by email
    suspend fun getUserByEmail(email: String): User? {
        val q = db.collection("users").whereEqualTo("email", email).limit(1).get().await()
        return if (!q.isEmpty) q.documents[0].toObject(User::class.java) else null
    }

    // Get user by uid
    suspend fun getUserByUid(uid: String): User? {
        val doc = db.collection("users").document(uid).get().await()
        return if (doc.exists()) doc.toObject(User::class.java) else null
    }

    // Send message and propagate to parent chain
    suspend fun sendMessageToParents(text: String): Result<Unit> {
        val fromUid = currentUid() ?: return Result.failure(Exception("Not logged in"))
        val fromUser = getUserByUid(fromUid) ?: return Result.failure(Exception("User not found"))
        val baseMessage = mapOf(
            "fromUid" to fromUid,
            "fromName" to fromUser.name,
            "text" to text,
            "createdAt" to System.currentTimeMillis()
        )
        return try {
            // 1) Create message in messages collection
            val msgRef = db.collection("messages").document()
            msgRef.set(baseMessage).await()
            val msgId = msgRef.id

            // 2) gather recipients (parent chain)
            val recipients = mutableSetOf<String>()
            // direct parent by parentEmail -> find user
            var currentParentEmail = fromUser.parentEmail
            while (!currentParentEmail.isNullOrBlank()) {
                val parent = getUserByEmail(currentParentEmail)
                if (parent != null) {
                    // add parent uid
                    if (!recipients.contains(parent.uid)) {
                        recipients.add(parent.uid)
                    }
                    // set next parent to parent's parentEmail
                    currentParentEmail = parent.parentEmail
                } else {
                    // parent email not found in db -> stop
                    break
                }
            }

            // 3) Also include connections: (optionally) you said "their connected users" —
            // For simplicity here we only propagate upward (parent chain). If you want lateral propagation
            // (siblings/children), we need a graph of connections. We can add later if needed.

            // 4) write message to each recipient's inbox
            for (uid in recipients) {
                db.collection("inbox").document(uid)
                    .collection("messages")
                    .document(msgId)
                    .set(baseMessage).await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Fetch inbox messages for current user (ordered by createdAt desc)
    suspend fun fetchInboxForCurrentUser(): List<Message> {
        val uid = currentUid() ?: return emptyList()
        val snap = db.collection("inbox").document(uid)
            .collection("messages")
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get().await()
        return snap.documents.mapNotNull { it.toObject(Message::class.java)?.copy(id = it.id) }
    }
}
