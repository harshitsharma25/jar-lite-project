package com.jar.app.model

data class User(
    val id : String = "",
    val name : String = "",
    val email : String = "",
    val image : String? = "",  // user's image
    val mobile : String? = "",
    val age : String? = ""
)