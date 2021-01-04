package com.yma.banks.repository.person

import com.yma.banks.api.PersonApiService
import com.yma.banks.model.PersonApiResponseContainer
import com.yma.banks.utils.SchedulerProvider
import io.reactivex.Single

class PersonRepositoryImpl(
    private val api: PersonApiService,
    private val schedulerProvider: SchedulerProvider
) : PersonRepository{
    override fun getPersonFromApi(): Single<PersonApiResponseContainer> {
        return api.getPersonFromApi(10).subscribeOn(schedulerProvider.io())
    }
}