package com.kuantum.movieapp.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kuantum.movieapp.R
import com.kuantum.movieapp.repository.MoviesRepository
import com.kuantum.movieapp.repository.MoviesRepositoryImpl
import com.kuantum.movieapp.roomdb.MoviesDao
import com.kuantum.movieapp.roomdb.MoviesDatabase
import com.kuantum.movieapp.service.MoviesAPI
import com.kuantum.movieapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, MoviesDatabase::class.java, "MoviesDatabase"
    ).build()

    @Singleton
    @Provides
    fun providesDao(database: MoviesDatabase) = database.dao()


    @Singleton
    @Provides
    fun providesMoviesApi() : MoviesAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MoviesAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesMoviesRepository(api: MoviesAPI, dao: MoviesDao) =
        MoviesRepositoryImpl(api = api, dao = dao) as MoviesRepository

    @Singleton
    @Provides
    fun providesGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}
