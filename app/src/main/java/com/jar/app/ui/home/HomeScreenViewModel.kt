package com.jar.app.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth : FirebaseAuth
): ViewModel(){
    private val _userName  = mutableStateOf<String?>("user")
    val userName : State<String?> = _userName

    init {
        fetchUserInfo()
    }

    fun fetchUserInfo(){
        val userId  = auth.currentUser?.uid ?: return
        val userDocRef = firestore.collection("users")
            .document(userId)

        userDocRef.get()
            .addOnSuccessListener { document ->
                val name = document.getString("name")
                _userName.value = name ?: "user"
            }.addOnFailureListener { exception ->
                println("Get failed with ,$exception")
                _userName.value = null
            }
    }
}