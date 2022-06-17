package pk.sadapay.trendingrepos.data.base

import androidx.viewbinding.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pk.sadapay.trendingrepos.BuildConfig.API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val timeoutRead = 30   //In seconds
private const val contentType = "Content-Type"
private const val contentTypeValue = "application/json"
private const val timeoutConnect = 30   //In seconds

class RetroNetwork {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit

    private val headerInterceptor = Interceptor { chain ->
        val original = chain.request()
        val url: HttpUrl = original.url.newBuilder()
            .build()

        val request = original.newBuilder()
            .header(contentType, contentTypeValue)
            .method(original.method, original.body)
            .url(url)
            .build()

        chain.proceed(request)
    }

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

    private fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request: Request = chain.request()
            val originalResponse = chain.proceed(request)

            val maxAge: Int = originalResponse.cacheControl.maxAgeSeconds
            if (maxAge <= 0) {
                originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Expires")
                    .removeHeader("Cache-Control")
                    .header(
                        "Cache-Control",
                        String.format(
                            Locale.ENGLISH,
                            "max-age=%d, only-if-cached, max-stale=%d",
                            60 * 60 * 24,
                            60 * 60 * 24
                        )
                    )
                    .build()
            } else {
                originalResponse
            }

        }
    }

    init {
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
        val client = okHttpBuilder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(API_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}
