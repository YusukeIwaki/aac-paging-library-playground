package jp.crowdworks.job_offer_search.api

import jp.crowdworks.job_offer_search.model.JobOfferSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JobOfferSearchApi {
    @GET("api/v1/public/job_offers_with_pr")
    fun getJobOffers(
            @Query("keywords")
            keywords: String,

            @Query("excluded_keywords")
            excludedKeywords: String,

            @Query("offset")
            offset: Long,

            @Query("per_page")
            perPage: Int
    ): Call<JobOfferSearchResult>
}
