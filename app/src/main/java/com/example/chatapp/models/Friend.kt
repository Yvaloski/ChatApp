package com.example.chatapp.models

import java.util.UUID

data class Friend(
    var uuid: String,
    val name: String,
    val lastMsg: String,
    val image: String,
    val timestamp:Long,

){
    constructor(): this("","","","",0)
}
