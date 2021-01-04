package com.yma.banks.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yma.banks.model.BanksApiResponseContainer
import com.yma.banks.repository.banks.BanksRepository
import com.yma.banks.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class BankListViewModel(
    private val repo: BanksRepository,
    private val schedulers: SchedulerProvider
) : ViewModel() {

    val errorLiveData: LiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val banksLiveData: LiveData<BanksApiResponseContainer> by lazy {
        MutableLiveData<BanksApiResponseContainer>()
    }

    private val compositeDisposable = CompositeDisposable()

    fun getBankListFromApi() {
        compositeDisposable.add(
            repo.getBanksFromApi()
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribe({
                    (banksLiveData as MutableLiveData<*>).value = it
                }, { error ->
                    (errorLiveData as MutableLiveData<*>).value = when (error) {
                        is HttpException -> "Server Error"
                        is SocketTimeoutException -> "Request Timeout"
                        is IOException -> "I/O error or disconnected"
                        else -> "Server Error"
                    }
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
