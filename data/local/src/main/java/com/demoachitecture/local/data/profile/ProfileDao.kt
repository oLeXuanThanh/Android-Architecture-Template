package com.demoachitecture.local.data.profile

import androidx.room.Dao
import androidx.room.Query
import com.demoachitecture.local.data.BaseDao

@Dao
abstract class ProfileDao: BaseDao<ProfileEntity> {

    @Query("SELECT * FROM Profile WHERE id = :profileId")
    abstract fun getProfileBy(profileId: String): ProfileEntity

    @Query("SELECT count(*) from Profile WHERE lastRefreshed > :freshTimeout")
    abstract fun hasProfile(freshTimeout: Long): Int
}