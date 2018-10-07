package jp.crowdworks.job_offer_search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.crowdworks.job_offer_search.databinding.ListItemJobOfferContainerBinding
import jp.crowdworks.job_offer_search.databinding.ListItemLoadingBinding
import jp.crowdworks.job_offer_search.model.JobOfferContainer

class JobOfferConainerAdapter() : PagedListAdapter<JobOfferContainer, JobOfferConainerAdapter.ItemViewHolder>(JobOfferContainerDiffUtilCallback()) {

    companion object {
        const val VIEW_TYPE_JOB_OFFER_CONTAINER = 0
        const val VIEW_TYPE_LOADING = 1
    }

    private var loadingBefore = false
    private var loadingAfter = false

    override fun getItemViewType(position: Int): Int {
        if ((loadingBefore && position == 0) || (loadingAfter && position == itemCount - 1)) {
            return VIEW_TYPE_LOADING
        } else {
            return VIEW_TYPE_JOB_OFFER_CONTAINER
        }
    }

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)
    private class JobOfferContainerViewHolder(val binding: ListItemJobOfferContainerBinding) : ItemViewHolder(binding.root)
    private class LoadingViewHolder(val binding: ListItemLoadingBinding) : ItemViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_TYPE_JOB_OFFER_CONTAINER -> {
                val binding = ListItemJobOfferContainerBinding.inflate(inflater, parent, false)
                return JobOfferContainerViewHolder(binding)
            }
            VIEW_TYPE_LOADING -> {
                val binding = ListItemLoadingBinding.inflate(inflater, parent, false)
                return LoadingViewHolder(binding)
            }
        }
        throw IllegalArgumentException("unknown viewType: ${viewType}")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if ((loadingBefore && position == 0) || (loadingAfter && position == itemCount - 1)) {
            return
        }

        (holder as? JobOfferContainerViewHolder)?.let { viewHolder ->
            viewHolder.binding.jobOfferContainer = super.getItem(position)
        }
    }

    override fun getItem(position: Int): JobOfferContainer? {
        if ((loadingBefore && position == 0) || (loadingAfter && position == itemCount - 1)) {
            return null
        }
        if (loadingBefore) {
            return super.getItem(position - 1)
        } else {
            return super.getItem(position)
        }
    }

    val jobOfferContainerItemCount: Int get() = super.getItemCount()

    override fun getItemCount(): Int {
        if (loadingBefore && loadingAfter) return jobOfferContainerItemCount + 2
        if (loadingBefore || loadingAfter) return jobOfferContainerItemCount + 1
        return jobOfferContainerItemCount
    }

    fun updateLoadingBefore(loading: Boolean) {
        val previousLoading = loadingBefore
        loadingBefore = loading
        if (loading) {
            if (previousLoading != loading) {
                notifyItemInserted(0)
            } else {
                notifyItemChanged(0)
            }
        } else {
            if (previousLoading != loading) {
                notifyItemRemoved(0)
            }
        }
    }

    fun updateLoadingAfter(loading: Boolean) {
        val previousLoading = loadingAfter
        loadingAfter = loading
        if (loading) {
            if (previousLoading != loading) {
                notifyItemInserted(itemCount - 1)
            } else {
                notifyItemChanged(itemCount - 1)
            }
        } else {
            if (previousLoading != loading) {
                notifyItemRemoved(itemCount)
            }
        }
    }
}
