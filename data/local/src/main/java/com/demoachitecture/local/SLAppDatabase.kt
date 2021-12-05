package com.demoachitecture.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demoachitecture.local.converter.Converters
import com.demoachitecture.local.data.friend.FriendEntity
import com.demoachitecture.local.data.friend.FriendDao

@Database(
    entities = [
        FriendEntity::class
               ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SLAppDatabase: RoomDatabase() {

    abstract fun friendDao(): FriendDao

    companion object {

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, SLAppDatabase::class.java, "SmartLottery.db")
                .allowMainThreadQueries()
                .build()
    }
}