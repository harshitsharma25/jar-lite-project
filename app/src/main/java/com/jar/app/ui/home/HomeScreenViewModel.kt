package com.jar.app.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jar.app.BuildConfig
import com.jar.app.data.paging.YouTubePagingSource
import com.jar.app.repository.JarRepository
import com.jar.app.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth : FirebaseAuth,
    private val repository: JarRepository
): ViewModel(){
    private val _userName  = mutableStateOf<String?>("user")
    val userName : State<String?> = _userName

    private val apiKey = BuildConfig.YOUTUBE_API_KEY
    private val channelId = Constants.JAR_CHANNEL_ID

    val videos = Pager(PagingConfig(pageSize = 10)){
        YouTubePagingSource(
            repository = repository,
            channelId = channelId,
            apiKey = apiKey
        )
    }.flow.cachedIn(viewModelScope)

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