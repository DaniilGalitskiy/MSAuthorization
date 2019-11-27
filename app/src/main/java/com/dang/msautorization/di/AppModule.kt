package com.dang.msautorization.di

import android.content.Context
import androidx.room.Room
import com.dang.msautorization.domain.authorization.UserAuthorizationModel
import com.dang.msautorization.domain.authorization.DefUserAuthorizationModel
import com.dang.msautorization.domain.connect_network.DefNetworkConnectModel
import com.dang.msautorization.domain.connect_network.NetworkConnectModel
import com.dang.msautorization.repository.db.DefAppDatabase
import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.net.Api
import com.dang.msautorization.repository.pref.DefSharedPrefsScreen
import com.dang.msautorization.repository.pref.SharedPrefsScreen
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class AppModule {

    companion object {
        private const val CONNECT_TIMEOUT: Long = 2500
        private const val URL: String = "https://api.github.com"
    }

    @Provides
    @Singleton
    fun authUserDao(context: Context): UserDao {
        return Room.databaseBuilder(context, DefAppDatabase::class.java, "github_authorization")
                .fallbackToDestructiveMigration()
                .build()
                .getUserDao()
    }

    @Provides
    fun api(): Api {
        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun defSharedPrefsRepo(context: Context): SharedPrefsScreen {
        return DefSharedPrefsScreen(context)
    }

    @Provides
    @Singleton
    fun defUserAuthorizationModel(userDao: UserDao, api: Api): UserAuthorizationModel {
        return DefUserAuthorizationModel(userDao, api)
    }

    @Provides
    @Singleton
    fun defNetworkConnectModel(): NetworkConnectModel {
        return DefNetworkConnectModel()
    }

}