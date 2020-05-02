package com.alpha2048.coroutinesflowtest

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule : Module = module {
    single { GithubRepositoryImpl() as GithubRepository }
}

val viewModelModule : Module = module {
    viewModel { MainViewModel(get()) }
}