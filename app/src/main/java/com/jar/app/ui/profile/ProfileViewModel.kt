package com.jar.app.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jar.app.model.User
import com.jar.app.repository.JarRepository

class ProfileViewModel(
    private val auth : FirebaseAuth,
    private val repository: JarRepository,
    private val firestore: FirebaseFirestore,
): ViewModel() {

    private val _user  = mutableStateOf<User?>(null)
    val user : State<User?> = _user

    init {
        fetchUserInfo()
    }

    fun fetchUserInfo(){
        val userId  = auth.currentUser?.uid ?: return
        val userDocRef = firestore.collection("users")
            .document(userId)

        userDocRef.get()
            .addOnSuccessListener { document ->
                val userData = document.toObject(User::class.java)
                _user.value = userData
            }.addOnFailureListener { exception ->
                println("Get failed with ,$exception")
                _user.value = null
            }
    }

}