package pk.sadapay.trendingrepos.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pk.sadapay.trendingrepos.data.repo.IGithubRepository
import pk.sadapay.trendingrepos.data.repo.remote.RemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideRemoteRepository(remoteRepository: RemoteRepository): IGithubRepository
}