package com.yma.banks.api

import com.yma.banks.model.BanksApiResponseContainer
import com.yma.banks.utils.ApiUrlConstant
import io.reactivex.Single
import retrofit2.http.GET

interface BanksApiService {

    @GET(ApiUrlConstant.BANK_LIST)
    fun getBanksFromApi(): Single<BanksApiResponseContainer>
}