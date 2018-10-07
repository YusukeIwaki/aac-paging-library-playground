package jp.crowdworks.job_offer_search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.crowdworks.job_offer_search.api.ApiClientFactory
import jp.crowdworks.job_offer_search.model.JobOfferSearchQuery
import jp.crowdworks.job_offer_search.paging.JobOfferSearchDataSourceFactory

class JobOfferSearchActivityViewModel : ViewModel() {
    private val dataSourceFactory = run {
        val api = ApiClientFactory.createJobOfferSearchApi()
        val query = JobOfferSearchQuery(keywords = "初心者")
        JobOfferSearchDataSourceFactory(api, query)
    }

    val jobOfferContainerList = run {
        val pagedListConfig = PagedList.Config.Builder()
                .setInitialLoadSizeHint(30)
                .setPageSize(30)
                .build()
        LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }

    val loadingInitial: LiveData<Boolean> = dataSourceFactory.loadingInitial
    val loadingBefore: LiveData<Boolean> = dataSourceFactory.loadingBefore
    val loadingAfter: LiveData<Boolean> = dataSourceFactory.loadingAfter

    fun refreshJobOfferContainerList() {
        dataSourceFactory.refresh()
    }
}
