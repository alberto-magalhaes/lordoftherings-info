package com.albertomagalhaes.lordoftheringsinfo.application

import androidx.navigation.NavController
import com.albertomagalhaes.lordoftheringsinfo.data.local.AppDatabase
import com.albertomagalhaes.lordoftheringsinfo.data.repository.AppRepositoryImpl
import com.albertomagalhaes.lordoftheringsinfo.domain.usecase.AppUseCaseImpl
import com.albertomagalhaes.lordoftheringsinfo.presentation.AppViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositoryModule = module {
    single { AppRepositoryImpl(
        movieDAO = get(),
        quoteDAO = get(),
        characterDAO = get()
    )
    }
}

val useCaseModule = module {
    single { AppUseCaseImpl(
        repository = get()
    )
    }
}

val viewModelModule = module {
    viewModel { (navController: NavController) ->
        AppViewModel(
            appUseCase = get(),
            navController = navController
        )
    }
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()).movieDAO }
    single { AppDatabase.getInstance(androidContext()).quoteDAO }
    single { AppDatabase.getInstance(androidContext()).characterDAO }
}