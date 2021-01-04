package com.yma.banks.api

import com.yma.banks.model.PersonApiResponseContainer
import com.yma.banks.utils.ApiUrlConstant
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonApiService {

    @GET(ApiUrlConstant.PERSON_LIST)
    fun getPersonFromApi(@Query("limit") limit: Int): Single<PersonApiResponseContainer>
}