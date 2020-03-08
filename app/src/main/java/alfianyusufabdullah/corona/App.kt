package alfianyusufabdullah.corona

import alfianyusufabdullah.corona.data.repository.DataRepository
import alfianyusufabdullah.corona.data.source.DataSource
import alfianyusufabdullah.corona.ui.MainViewModel
import alfianyusufabdullah.corona.util.Mapper
import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

@ExperimentalCoroutinesApi
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            modules(appModule)
        }
    }
}

@ExperimentalCoroutinesApi
val appModule = module {
    single { DataSource() }
    single { DataRepository(get()) }

    single { Mapper() }

    viewModel { MainViewModel(get(), get()) }
}