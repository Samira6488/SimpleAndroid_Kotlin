package ir.matiran.kotlin_sample.viewModel

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.exir.io/v1/trades/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun apiProvider(retrofit: Retrofit) : ApiInterface = retrofit.create(ApiInterface :: class.java)

    @Provides
    fun repository(apiService: ApiInterface) : Repository = Repository(apiService)



}