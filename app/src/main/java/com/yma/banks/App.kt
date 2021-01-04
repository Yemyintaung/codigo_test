package com.yma.banks

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.yma.banks.di.ViewModelFactory
import com.yma.banks.di.bindViewModel
import com.yma.banks.di.networkModule
import com.yma.banks.viewmodel.PersonListViewModel
import com.yma.banks.repository.person.PersonRepository
import com.yma.banks.repository.person.PersonRepositoryImpl
import com.yma.banks.room.AppDatabase
import com.yma.banks.room.DatabaseBuilder
import com.yma.banks.utils.SchedulerProvider
import com.yma.banks.utils.SchedulerProviderImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.direct
import org.kodein.di.generic.*

class App : MultiDexApplication(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))
        bind<Context>("ApplicationContext") with singleton { this@App.applicationContext }
        import(networkModule)
        bind<SchedulerProvider>() with singleton { SchedulerProviderImpl() }
        bind<PersonRepository>() with singleton { PersonRepositoryImpl(instance(), instance()) }
        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

        bind<AppDatabase>() with eagerSingleton {
            DatabaseBuilder.getInstance(applicationContext)
        }
        bindViewModel<PersonListViewModel>() with provider {
            PersonListViewModel(
                instance(),
                instance(),
                instance()
            )
        }
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}