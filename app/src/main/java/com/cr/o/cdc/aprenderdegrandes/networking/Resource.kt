package com.cr.o.cdc.aprenderdegrandes.networking

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

sealed class Resource<T> {

    abstract val data: T?

    data class Success<T>(override val data: T) : Resource<T>()
    data class Loading<T>(override val data: T? = null) : Resource<T>()
    data class Error<T>(val message: String?, override val data: T? = null) : Resource<T>()
}

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow<Resource<ResultType>> {
    emit(Resource.Loading(null))
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            val result = fetch()
            withContext(Dispatchers.IO) {
                saveFetchResult(result)
            }
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Resource.Error(throwable.message, it) }
            throw throwable
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}
