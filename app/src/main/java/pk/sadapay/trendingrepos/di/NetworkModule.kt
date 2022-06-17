package pk.sadapay.trendingrepos.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import pk.sadapay.trendingrepos.data.base.RetroNetwork
import pk.sadapay.trendingrepos.data.repo.remote.GithubRepositoryService
import java.io.File

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val diskCacheSize = (10 * 1024 * 1024).toLong() // 10 MB

    @Provides
    fun providesReposService(): GithubRepositoryService =
        RetroNetwork().createService(GithubRepositoryService::class.java)

    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheDir = File(context.cacheDir, "staleData")
        return Cache(cacheDir, diskCacheSize)
    }
}