package com.jar.app.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jar.app.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth : FirebaseAuth,
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

    fun updateField(fieldName : String,value :String){
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .update(fieldName,value)
            .addOnSuccessListener {
                println("Field name $fieldName is updated!!")
            }.addOnFailureListener {
                println("Field name $fieldName not updated , some error occured")
            }
    }

}