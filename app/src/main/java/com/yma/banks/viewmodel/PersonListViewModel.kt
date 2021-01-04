package com.yma.banks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yma.banks.model.PersonApiResponse
import com.yma.banks.model.PersonApiResponseContainer
import com.yma.banks.repository.person.PersonRepository
import com.yma.banks.room.AppDatabase
import com.yma.banks.room.models.Person
import com.yma.banks.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class PersonListViewModel(
    private val appDatabase: AppDatabase,
    private val repo: PersonRepository,
    private val schedulers: SchedulerProvider
) : ViewModel() {

    val errorLiveData: LiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val personLiveData: LiveData<PersonApiResponseContainer> by lazy {
        MutableLiveData<PersonApiResponseContainer>()
    }

    private val compositeDisposable = CompositeDisposable()

    fun getPersonListFromApi() {
        compositeDisposable.add(
            repo.getPersonFromApi()
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribe({
                    (personLiveData as MutableLiveData<*>).value = savePersonInfoToRoom(it)
                }, { error ->
                    (errorLiveData as MutableLiveData<*>).value = when (error) {
                        is HttpException -> "Server Error"
                        is SocketTimeoutException -> "Request Timeout"
                        is IOException -> (personLiveData as MutableLiveData<*>).value =
                            getAllPersonInfoFromLocal()
                        else -> "Server Error"
                    }
                })
        )
    }


    private fun getAllPersonInfoFromLocal(): PersonApiResponseContainer {
        val dataList = ArrayList<PersonApiResponse>()
        val thread = Thread {
            val bankCount = appDatabase.PersonDao().getPersonCount()
            if (bankCount > 0) {
                val localBankDataList = appDatabase.PersonDao().getAllPerson()
                for (item in localBankDataList) {
                    val data = PersonApiResponse()
                    data.id = item.personId
                    data.lastName = item.lastName
                    data.firstName = item.firstName
                    data.email = item.email
                    data.title = item.title
                    data.picture = item.picture
                    dataList.add(data)
                }
            }
        }
        thread.start()
        thread.join()
        return PersonApiResponseContainer(0, "offline", dataList)
    }

    private fun savePersonInfoToRoom(data: PersonApiResponseContainer): PersonApiResponseContainer {
        GlobalScope.launch(Dispatchers.IO) {
            appDatabase.PersonDao().deleteAll()
            val dataList = ArrayList<Person>()

            for (item in data.data) {
                val data = Person()
                data.personId = item.id
                data.lastName = item.lastName
                data.firstName = item.firstName
                data.email = item.email
                data.title = item.title
                data.picture = item.picture
                dataList.add(data)
            }

            appDatabase.PersonDao().addAll(dataList)
        }
        return data
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
