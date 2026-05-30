package dev.kick.signinorsignup.core.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.kick.signinorsignup.core.data.local.AuthDatabase
import dev.kick.signinorsignup.core.data.local.UserDao
import dev.kick.signinorsignup.core.data.repository.AuthRepositoryImpl
import dev.kick.signinorsignup.core.data.source.local.AuthLocalDataSource
import dev.kick.signinorsignup.core.data.source.local.AuthLocalDataSourceImpl
import dev.kick.signinorsignup.core.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideAuthDatabase(
        @ApplicationContext context: Context,
    ): AuthDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AuthDatabase::class.java,
            name = "auth.db",
        ).build()
    }

    @Provides
    fun provideUserDao(database: AuthDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(userDao: UserDao): AuthLocalDataSource {
        return AuthLocalDataSourceImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(localDataSource: AuthLocalDataSource): AuthRepository {
        return AuthRepositoryImpl(localDataSource)
    }
}
