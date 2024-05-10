package com.aditprayogo.core.di

import com.aditprayogo.core.data.repository.attendance.AttendanceRepository
import com.aditprayogo.core.data.repository.attendance.AttendanceRepositoryImpl
import com.aditprayogo.core.data.repository.auth.AuthRepository
import com.aditprayogo.core.data.repository.auth.AuthRepositoryImpl
import com.aditprayogo.core.data.repository.office.OfficeRepository
import com.aditprayogo.core.data.repository.office.OfficeRepositoryImpl
import com.aditprayogo.core.data.repository.preferences.PreferencesRepository
import com.aditprayogo.core.data.repository.preferences.PreferencesRepositoryImpl
import com.aditprayogo.core.data.repository.profile.ProfileRepository
import com.aditprayogo.core.data.repository.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideOfficeRepository(officeRepositoryImpl: OfficeRepositoryImpl): OfficeRepository

    @Binds
    abstract fun providePreferencesRepository(preferencesRepositoryImpl: PreferencesRepositoryImpl): PreferencesRepository

    @Binds
    abstract fun provideProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideAttendanceRepository(attendanceRepositoryImpl: AttendanceRepositoryImpl): AttendanceRepository
}