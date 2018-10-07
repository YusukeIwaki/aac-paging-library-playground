package jp.crowdworks.job_offer_search.model

import com.squareup.moshi.Json

data class JobOfferSearchResult(
        @Json(name = "job_offer_containers")
        val jobOfferContainers: List<JobOfferContainer>,

        @Json(name = "has_next")
        val hasNext: Boolean,

        @Json(name = "total_count")
        val totalCount: Long
)
