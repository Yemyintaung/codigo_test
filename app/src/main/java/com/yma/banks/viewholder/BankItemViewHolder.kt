package com.yma.banks.viewholder

import android.app.Activity
import android.util.DisplayMetrics
import androidx.recyclerview.widget.RecyclerView
import com.yma.banks.databinding.ActivityBankItemBinding
import com.yma.banks.model.BanksApiResponse

class BankItemViewHolder(private val binding: ActivityBankItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BanksApiResponse) {
        /*val act = binding.activityBankItemName.context as Activity
        val displayMetrics = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(displayMetrics)
        binding.activityBankItemName.layoutParams.width = displayMetrics.widthPixels*/
        binding.item = item
        binding.executePendingBindings()
    }
}