package jp.crowdworks.job_offer_search.model

import com.squareup.moshi.Json

data class JobOfferContainer(
        @Json(name = "job_offer")
        val jobOffer: JobOffer
)