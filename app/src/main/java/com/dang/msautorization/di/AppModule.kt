package com.dang.msautorization.di

import android.content.Context
import androidx.room.Room
import com.dang.msautorization.domain.authorization.DefUserAuthorizationModel
import com.dang.msautorization.domain.authorization.UserAuthorizationModel
import com.dang.msautorization.domain.connect_network.DefNetworkConnectModel
import com.dang.msautorization.domain.connect_network.NetworkConnectModel
import com.dang.msautorization.domain.user_info.DefUserInfoModel
import com.dang.msautorization.domain.user_info.UserInfoModel
import com.dang.msautorization.repository.db.AppDatabase
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

    @Provides
    @Singleton
    fun db(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "github_authorization")
                    .fallbackToDestructiveMigration()
                    .build()


    @Provides
    fun userDao(db: AppDatabase): UserDao = db.getUserDao()



    @Provides
    fun api(): Api {
        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(Api.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(Api.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun defSharedPrefsRepo(context: Context): SharedPrefsScreen = DefSharedPrefsScreen(context)

    @Provides
    @Singleton
    fun defUserAuthorizationModel(userDao: UserDao, api: Api): UserAuthorizationModel =
            DefUserAuthorizationModel(userDao, api)

    @Provides
    @Singleton
    fun defUserInfoModel(userDao: UserDao, api: Api): UserInfoModel =
            DefUserInfoModel(userDao, api)

    @Provides
    @Singleton
    fun defNetworkConnectModel(): NetworkConnectModel = DefNetworkConnectModel()

}