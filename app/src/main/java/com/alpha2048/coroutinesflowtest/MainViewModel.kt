package com.alpha2048.coroutinesflowtest

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    val trigger = BroadcastChannel<Unit>(1)

    // LiveDataで連携させる場合はこれ
//    val liveDataResponse: LiveData<RepoResponse> = liveData {
//        emitSource(GithubRepository().getRepositoryList("Coroutine", 2).asLiveData())
//    }

    val repoItems = ArrayList<RepoItemResponse>()

    fun loadData() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getRepositoryList("Coroutine", 1).collect{
                repoItems.addAll(it.items)
                trigger.send(Unit)
            }
        }
    }
}