package com.example.room.mvvm.utils

import android.content.Context
import android.service.autofill.UserData
import androidx.room.Room
import com.example.room.mvvm.repository.LoginRepository
import com.example.room.mvvm.room.DAOAccess
import com.example.room.mvvm.room.LoginDatabase
import com.example.room.mvvm.utils.Util.Companion.MIGRATION_1_2
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):LoginDatabase=
        Room.databaseBuilder(context, LoginDatabase::class.java, "LOGIN_DATABASE")
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    @Singleton
    fun provideUserDao(loginDatabase: LoginDatabase): DAOAccess = loginDatabase.loginDao()
}