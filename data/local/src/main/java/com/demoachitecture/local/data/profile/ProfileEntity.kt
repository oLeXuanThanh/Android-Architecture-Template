package com.demoachitecture.local.data.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Profile")
class ProfileEntity {
    @PrimaryKey
    var id: String = ""

    val avatarUrl: String = ""

    var name: String? = null

    var lastRefreshed: Date? = null
}