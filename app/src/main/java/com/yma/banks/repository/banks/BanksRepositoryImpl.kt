package com.yma.banks.repository.banks

import com.yma.banks.api.BanksApiService
import com.yma.banks.model.BanksApiResponseContainer
import com.yma.banks.utils.SchedulerProvider
import io.reactivex.Single

class BanksRepositoryImpl(
    private val api: BanksApiService,
    private val schedulerProvider: SchedulerProvider
) : BanksRepository{
    override fun getBanksFromApi(): Single<BanksApiResponseContainer> {
        return api.getBanksFromApi().subscribeOn(schedulerProvider.io())
    }
}