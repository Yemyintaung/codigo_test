package com.yma.banks.repository.banks

import com.yma.banks.model.BanksApiResponseContainer
import io.reactivex.Single

interface BanksRepository {
    fun getBanksFromApi() : Single<BanksApiResponseContainer>
}