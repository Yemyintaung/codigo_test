package com.yma.banks.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.yma.banks.databinding.ActivityPersonItemBinding
import com.yma.banks.model.PersonApiResponse

class PersonItemViewHolder(private val binding: ActivityPersonItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PersonApiResponse) {
        /*val act = binding.activityBankItemName.context as Activity
        val displayMetrics = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(displayMetrics)
        binding.activityBankItemName.layoutParams.width = displayMetrics.widthPixels*/
        binding.item = item
        binding.executePendingBindings()
    }
}