package jp.crowdworks.job_offer_search.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import jp.crowdworks.job_offer_search.api.JobOfferSearchApi
import jp.crowdworks.job_offer_search.model.JobOfferContainer
import jp.crowdworks.job_offer_search.model.JobOfferSearchQuery
import jp.crowdworks.job_offer_search.model.JobOfferSearchResult
import java.io.IOException

class JobOfferSearchDataSource(private val api: JobOfferSearchApi, private val query: JobOfferSearchQuery) : PageKeyedDataSource<Long, JobOfferContainer>() {

    val networkState = MutableLiveData<NetworkState>()

    val loadingInitial = MutableLiveData<Boolean>()
    val loadingBefore = MutableLiveData<Boolean>()
    val loadingAfter = MutableLiveData<Boolean>()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, JobOfferContainer>) {
        load(0, params.requestedLoadSize, loadingInitial) { result ->
            if (result.hasNext) {
                callback.onResult(result.jobOfferContainers, null, result.jobOfferContainers.size.toLong())
            } else {
                callback.onResult(result.jobOfferContainers, null, null)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, JobOfferContainer>) {
        load(params.key, params.requestedLoadSize, loadingAfter) { result ->
            if (result.hasNext) {
                callback.onResult(result.jobOfferContainers, params.key + result.jobOfferContainers.size.toLong())
            } else {
                callback.onResult(result.jobOfferContainers, null)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, JobOfferContainer>) {
        load(params.key, params.requestedLoadSize, loadingBefore) { result ->
            if (result.hasNext) {
                callback.onResult(result.jobOfferContainers, params.key - result.jobOfferContainers.size.toLong())
            } else {
                callback.onResult(result.jobOfferContainers, null)
            }
        }
    }

    private fun load(offset: Long, perPage: Int, loadingLiveData: MutableLiveData<Boolean>, processResult: (JobOfferSearchResult) -> Unit) {
        loadingLiveData.postValue(true)
        networkState.postValue(NetworkState.FETCHING)
        try {
            val response = api.getJobOffers(query.keywords, query.excludedKeywords, offset, perPage).execute()
            if (response.isSuccessful) {
                networkState.postValue(NetworkState.SUCCESS)
                response.body()?.let { processResult(it) }
            } else {
                networkState.postValue(NetworkState.FAILURE)
            }
        } catch (e: IOException) {
            Log.w("JobOfferSearch", "error", e)
            networkState.postValue(NetworkState.FAILURE)
        }
        loadingLiveData.postValue(false)
    }
}
