package com.yma.banks.repository.person

import com.yma.banks.model.PersonApiResponseContainer
import io.reactivex.Single

interface PersonRepository {
    fun getPersonFromApi() : Single<PersonApiResponseContainer>
}