package jp.crowdworks.job_offer_search.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import jp.crowdworks.job_offer_search.api.JobOfferSearchApi
import jp.crowdworks.job_offer_search.model.JobOfferContainer
import jp.crowdworks.job_offer_search.model.JobOfferSearchQuery

class JobOfferSearchDataSourceFactory(private val api: JobOfferSearchApi, private val query: JobOfferSearchQuery) : DataSource.Factory<Long, JobOfferContainer>() {
    private val lastDataSource = MutableLiveData<JobOfferSearchDataSource>()

    val loadingInitial: LiveData<Boolean> = Transformations.switchMap(lastDataSource) { dataSource -> dataSource.loadingInitial }
    val loadingBefore: LiveData<Boolean> = Transformations.switchMap(lastDataSource) { dataSource -> dataSource.loadingBefore }
    val loadingAfter: LiveData<Boolean> = Transformations.switchMap(lastDataSource) { dataSource -> dataSource.loadingAfter }
    val networkState: LiveData<NetworkState> = Transformations.switchMap(lastDataSource) { dataSource -> dataSource.networkState }

    override fun create(): DataSource<Long, JobOfferContainer> {
        return JobOfferSearchDataSource(api, query).also { lastDataSource.postValue(it) }
    }

    fun refresh() {
        lastDataSource.value?.invalidate()
    }
}
