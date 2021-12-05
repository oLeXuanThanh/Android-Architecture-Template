package com.demoachitecture.local.di

import android.content.Context
import com.demoachitecture.local.SLAppDatabase
import com.demoachitecture.local.data.friend.FriendDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext application: Context): SLAppDatabase {
        return SLAppDatabase.buildDatabase(application)
    }

    @Provides
    @Singleton
    fun provideDeckDao(db: SLAppDatabase): FriendDao {
        return db.friendDao()
    }

}
