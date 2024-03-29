package com.example.practicesession.module

import android.content.Context
import androidx.room.Room
import com.example.practicesession.newapp.database.AppDatabase
import com.example.practicesession.newapp.database.ArticleDBDao
import com.example.practicesession.newapp.remote.RemoteInterface
import com.example.practicesession.utils.Constant
import com.example.practicesession.superballgame.database.SuperBallAppDatabase
import com.example.practicesession.superballgame.database.SuperBallDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofitObject(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

    @Provides
    @Singleton
    fun getRetrofitService(clientr: Retrofit): RemoteInterface {
        return clientr.create(RemoteInterface::class.java)
    }


    @Provides
    @Singleton
    fun getRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "article_db.db"
        ).allowMainThreadQueries().build()
    }


    @Provides
    @Singleton
    fun provideChannelDao(appDatabase: AppDatabase): ArticleDBDao {
        return appDatabase.getArticleDBDao()
    }


    @Provides
    @Singleton
    fun getRoomDatabaseSuper(@ApplicationContext context: Context): SuperBallAppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SuperBallAppDatabase::class.java,
            "super_db.db").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideChannelDaoSuper( appDatabase: SuperBallAppDatabase): SuperBallDao {
        return appDatabase.getArticleDBDao()
    }
}