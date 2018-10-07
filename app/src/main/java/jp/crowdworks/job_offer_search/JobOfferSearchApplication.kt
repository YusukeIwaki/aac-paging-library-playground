package jp.crowdworks.job_offer_search

import android.app.Application
import com.facebook.stetho.Stetho
import jp.crowdworks.job_offer_search.api.ApiClientFactory

class JobOfferSearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        ApiClientFactory.initializeWith(this)
    }
}
