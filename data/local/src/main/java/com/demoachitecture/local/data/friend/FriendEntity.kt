package com.demoachitecture.local.data.friend

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Friend")
data class FriendEntity (
    @PrimaryKey
    val id: String,

    val avatarUrl: String,

    val name: String?,

    var lastRefreshed: Date
)