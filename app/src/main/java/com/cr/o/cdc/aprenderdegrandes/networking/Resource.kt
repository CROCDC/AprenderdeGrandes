package com.cr.o.cdc.aprenderdegrandes.networking

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

sealed class Resource<T> {

    abstract val data: T?
    data class Success<T>(override val data: T) : Resource<T>()
    data class Loading<T>(override val data: T? = null) : Resource<T>()
    data class Error<T>(val message: String?, override val data: T? = null) : Resource<T>()

}

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Loading<T>(val data: T? = null) : ApiResponse<T>()
    data class Error<T>(val message: String, val data: T? = null) : ApiResponse<T>()

    fun isSuccessful() = this is Success

    fun errorMessage(): String? = (this as? Error)?.message
}

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow<Resource<ResultType>> {
    emit(Resource.Loading(null))
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Resource.Error(throwable.message, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}
