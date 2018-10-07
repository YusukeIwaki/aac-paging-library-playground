package jp.crowdworks.job_offer_search.model

data class JobOfferSearchQuery(
        val keywords: String = "",
        val excludedKeywords: String = ""
)