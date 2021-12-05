package com.demoachitecture.repository.util

import com.demoachitecture.common.network.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

// ResultType: Type for the Resource data.
// RequestType: Type for the API response.
@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDb: () -> Flow<ResultType>, // Called to get the cached data from the database.
    crossinline fetchRemote: suspend () -> RequestType, // Called to create the API call.
    crossinline saveFetchResult: suspend (RequestType) -> Unit, // Called to save the result of the API response into the database
    crossinline clearData: suspend () -> Unit, // Clear data from the database
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit }, // callback when fetchRemote failed
    crossinline shouldFetch: (ResultType?) -> Boolean = { true }, // Called with the data in the database to decide whether to fetch potentially updated data from the network.
    crossinline shouldClear: (RequestType, ResultType) -> Boolean = { requestType: RequestType, resultType: ResultType -> false }
) = flow<Resource<ResultType>> {

    emit(Resource.Loading(null))

    val dbData = loadFromDb().first()
    val flow = if (shouldFetch(dbData)) {
        emit(Resource.Loading(dbData))
        try {
            val fetchData = fetchRemote()
            if (shouldClear(fetchData, dbData)) {
                clearData()
            }
            saveFetchResult(fetchData)
            loadFromDb().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            loadFromDb().map { Resource.Error(throwable.message, it) }
        }
    } else {
        loadFromDb().map { Resource.Success(it) }
    }
    emitAll(flow)
}