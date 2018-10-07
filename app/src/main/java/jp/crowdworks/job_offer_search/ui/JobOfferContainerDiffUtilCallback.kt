package jp.crowdworks.job_offer_search.ui

import androidx.recyclerview.widget.DiffUtil
import jp.crowdworks.job_offer_search.model.JobOfferContainer

class JobOfferContainerDiffUtilCallback : DiffUtil.ItemCallback<JobOfferContainer>() {
    override fun areItemsTheSame(oldItem: JobOfferContainer, newItem: JobOfferContainer): Boolean {
        return oldItem.jobOffer.id == newItem.jobOffer.id
    }

    override fun areContentsTheSame(oldItem: JobOfferContainer, newItem: JobOfferContainer): Boolean {
        return oldItem.jobOffer == newItem.jobOffer
    }
}
