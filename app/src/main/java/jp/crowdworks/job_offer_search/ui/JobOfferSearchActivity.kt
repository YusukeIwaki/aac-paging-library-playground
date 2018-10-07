package jp.crowdworks.job_offer_search.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.crowdworks.job_offer_search.R
import jp.crowdworks.job_offer_search.databinding.ActivityJobOfferSearchBinding

class JobOfferSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobOfferSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_offer_search)
        val viewModel = ViewModelProviders.of(this).get(JobOfferSearchActivityViewModel::class.java)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = JobOfferConainerAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshJobOfferContainerList()
        }

        viewModel.jobOfferContainerList.observe(this, Observer { pagedList ->
            adapter.submitList(pagedList)
        })
        viewModel.loadingInitial.observe(this, Observer { loading ->
            val isRefreshing = loading == true
            if (binding.swipeRefreshLayout.isRefreshing != isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = isRefreshing
            }
        })
        viewModel.loadingBefore.observe(this, Observer {
            adapter.updateLoadingBefore(it == true)
        })
        viewModel.loadingAfter.observe(this, Observer {
            adapter.updateLoadingAfter(it == true)
        })
    }
}
